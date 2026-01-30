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
  public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
    AccessTokenCustomizer accessTokenCustomizer = new AccessTokenCustomizer();
    IdTokenCustomizer idTokenCustomizer = new IdTokenCustomizer();
    return context -> {
      accessTokenCustomizer.customize(context);
      idTokenCustomizer.customize(context);
    };
  }
}
