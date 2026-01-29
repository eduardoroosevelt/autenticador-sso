package com.example.sso.config;

import com.example.sso.token.AccessTokenCustomizer;
import com.example.sso.token.IdTokenCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class TokenCustomizationConfig {
  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> accessTokenCustomizer() {
    return new AccessTokenCustomizer();
  }

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> idTokenCustomizer() {
    return new IdTokenCustomizer();
  }
}
