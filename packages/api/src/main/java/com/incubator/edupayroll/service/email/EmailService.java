package com.incubator.edupayroll.service.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final AmazonSimpleEmailService emailService;

    @Value("${mailer.from.address}")
    private String fromAddress;

    @Autowired
    public EmailService(AmazonSimpleEmailService emailService) {
        this.emailService = emailService;
    }

    public void send(String to, String subject, String body) {
        var request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content().withCharset("UTF-8").withData(body))
                                .withText(new Content().withCharset("UTF-8").withData(body)))
                        .withSubject(new Content().withCharset("UTF-8").withData(subject)))
                .withSource(fromAddress);

        emailService.sendEmail(request);
    }

}
