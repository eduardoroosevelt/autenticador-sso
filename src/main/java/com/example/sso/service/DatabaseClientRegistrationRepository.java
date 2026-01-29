package com.example.sso.service;

import com.example.sso.model.OAuth2ClientRegistrationEntity;
import com.example.sso.repository.OAuth2ClientRegistrationRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseClientRegistrationRepository implements ClientRegistrationRepository {
  private final OAuth2ClientRegistrationRepository repository;

  public DatabaseClientRegistrationRepository(OAuth2ClientRegistrationRepository repository) {
    this.repository = repository;
  }

  @Override
  public ClientRegistration findByRegistrationId(String registrationId) {
    Optional<OAuth2ClientRegistrationEntity> entity = repository.findByRegistrationId(registrationId);
    return entity.map(this::toClientRegistration).orElse(null);
  }

  private ClientRegistration toClientRegistration(OAuth2ClientRegistrationEntity entity) {
    Set<String> scopes = parseScopes(entity.getScopes());
    return ClientRegistration.withRegistrationId(entity.getRegistrationId())
        .clientId(entity.getClientId())
        .clientSecret(entity.getClientSecret())
        .authorizationUri(entity.getAuthorizationUri())
        .tokenUri(entity.getTokenUri())
        .userInfoUri(entity.getUserInfoUri())
        .userNameAttributeName(entity.getUserNameAttribute())
        .issuerUri(entity.getIssuerUri())
        .clientName(entity.getClientName())
        .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
        .scope(scopes)
        .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
        .build();
  }

  private Set<String> parseScopes(String scopes) {
    if (scopes == null || scopes.isBlank()) {
      return Set.of();
    }
    List<String> tokens = Arrays.stream(scopes.split("[,\\s]+"))
        .map(String::trim)
        .filter(token -> !token.isBlank())
        .toList();
    return tokens.stream().collect(Collectors.toSet());
  }
}
