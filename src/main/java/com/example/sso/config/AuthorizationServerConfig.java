package com.example.sso.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class AuthorizationServerConfig {
  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults());
    http.exceptionHandling(
        exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
    );
    return http.build();
  }

  @Bean
  RegisteredClientRepository registeredClientRepository(DataSource dataSource) {
    return new JdbcRegisteredClientRepository(new JdbcTemplate(dataSource));
  }

  @Bean
  OAuth2AuthorizationService authorizationService(DataSource dataSource, RegisteredClientRepository registeredClientRepository) {
    return new org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService(
        new JdbcTemplate(dataSource), registeredClientRepository
    );
  }

  @Bean
  OAuth2AuthorizationConsentService authorizationConsentService(
      DataSource dataSource,
      RegisteredClientRepository registeredClientRepository
  ) {
    return new org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService(
        new JdbcTemplate(dataSource), registeredClientRepository
    );
  }

  @Bean
  AuthorizationServerSettings authorizationServerSettings(@Value("${app.issuer-uri}") String issuerUri) {
    return AuthorizationServerSettings.builder().issuer(issuerUri).build();
  }

  @Bean
  TokenSettings tokenSettings() {
    return TokenSettings.builder()
        .reuseRefreshTokens(false)
        .build();
  }

  @Bean
  JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  private RSAKey generateRsa() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
      return new RSAKey.Builder(publicKey)
          .privateKey(privateKey)
          .keyID(UUID.randomUUID().toString())
          .build();
    } catch (Exception ex) {
      throw new IllegalStateException("Falha ao gerar chave RSA", ex);
    }
  }
}
