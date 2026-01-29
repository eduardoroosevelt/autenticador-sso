package com.example.sso.token;

import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

public class IdTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
  @Override
  public void customize(JwtEncodingContext context) {
    if (!OAuth2TokenType.ID_TOKEN.equals(context.getTokenType())) {
      return;
    }
    context.getClaims().claim("auth_method", "password_or_social");
  }
}
