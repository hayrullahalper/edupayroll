package com.incubator.edupayroll.service.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.github.mustachejava.DefaultMustacheFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final Environment env;
  private final AmazonSimpleEmailService emailService;

  @Autowired
  public EmailService(Environment env, AmazonSimpleEmailService emailService) {
    this.env = env;
    this.emailService = emailService;
  }

  public void sendRegisterCompleteEmail(String to, String name) {
    sendEmail(to, "auth/register-complete", Map.of("name", name));
  }

  public void sendResetPasswordEmail(String email, String token) {
    try {
      var ub = new URIBuilder(env.getProperty("app.client-url"));
      ub.setPath(env.getProperty("app.client-reset-password-complete-path"));
      ub.setParameter("token", token);

      sendEmail(email, "auth/reset-password", Map.of("reset_password_url", ub.build().toString()));
    } catch (URISyntaxException e) {
      throw new EmailCreatingException("Failed to create reset password URL", e);
    }
  }

  public void sendRegisterConfirmationEmail(String to, String token) {
    try {
      var ub = new URIBuilder(env.getProperty("app.client-url"));
      ub.setPath(env.getProperty("app.client-register-complete-path"));
      ub.setParameter("token", token);

      sendEmail(
          to, "auth/register-confirmation", Map.of("verification_url", ub.build().toString()));
    } catch (URISyntaxException e) {
      throw new EmailCreatingException("Failed to create email verification URL", e);
    }
  }

  private void sendEmail(String to, String template, Map<String, Object> context) {
    try {
      var html = getTemplate(template, context);
      var source = getSource();
      var subject = getSubject(html);

      var request =
          new SendEmailRequest()
              .withDestination(new Destination().withToAddresses(to))
              .withMessage(
                  new Message()
                      .withBody(
                          new Body()
                              .withHtml(new Content().withCharset("UTF-8").withData(html))
                              .withText(new Content().withCharset("UTF-8").withData(html)))
                      .withSubject(new Content().withCharset("UTF-8").withData(subject)))
              .withSource(source)
              .withReplyToAddresses(env.getProperty("mailer.reply.address"));

      emailService.sendEmail(request);
    } catch (AmazonSimpleEmailServiceException e) {
      throw new EmailSendingException("Failed to send email", e);
    }
  }

  private String getTemplate(String template, Map<String, Object> context) {
    try {
      var mf = new DefaultMustacheFactory();
      var name =
          env.getProperty("spring.mustache.prefix")
              + template
              + env.getProperty("spring.mustache.suffix");

      var mustache = mf.compile(name);

      var writer = new StringWriter();
      var variables = new HashMap<>(context);

      variables.put("client_url", env.getProperty("app.client-url"));
      variables.put("client_logo_path", env.getProperty("app.client-logo-path"));

      mustache.execute(writer, variables).flush();

      return writer.toString();
    } catch (IOException e) {
      throw new EmailCreatingException("Failed to create email", e);
    }
  }

  private String getSubject(String html) {
    var doc = Jsoup.parse(html);
    return doc.head().select("title").text();
  }

  private String getSource() {
    return env.getProperty("mailer.from.name")
        + " <"
        + env.getProperty("mailer.from.address")
        + ">";
  }
}
