package com.incubator.edupayroll.configuration.security;

import com.incubator.edupayroll.service.token.InvalidTokenException;
import com.incubator.edupayroll.service.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired private TokenService tokenService;

  @Autowired private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    try {
      String token = extractToken(request);

      if (token != null && tokenService.verify(token)) {

        var claims = tokenService.decode(token);

        var userId = claims.get("userId").asString();
        var verified = claims.get("verified").asBoolean();

        if (!verified) {
          throw InvalidTokenException.byInvalidToken(token);
        }

        var details = userDetailsService.loadUserByUsername(userId);
        var auth = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception ignored) {
    }

    chain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    var token = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (token != null && token.startsWith("Bearer ")) {
      return token.substring(7);
    }

    return null;
  }
}
