package com.incubator.edupayroll.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubator.edupayroll.controller.auth.AuthErrorCode;
import com.incubator.edupayroll.util.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
  private final ObjectMapper mapper;

  public AuthEntryPoint(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    if (response.getStatus() != HttpServletResponse.SC_OK) {
      return;
    }

    var error = Response.error(AuthErrorCode.INVALID_CREDENTIALS, "Invalid credentials").build();

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write(mapper.writeValueAsString(error));
  }
}
