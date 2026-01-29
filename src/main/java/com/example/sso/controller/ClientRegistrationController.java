package com.example.sso.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientRegistrationController {
  private final RegisteredClientRepository registeredClientRepository;
  private final TokenSettings tokenSettings;

  public ClientRegistrationController(
      RegisteredClientRepository registeredClientRepository,
      TokenSettings tokenSettings
  ) {
    this.registeredClientRepository = registeredClientRepository;
    this.tokenSettings = tokenSettings;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RegisteredClientResponse registerClient(@Validated @RequestBody RegisteredClientRequest request) {
    Set<AuthorizationGrantType> grantTypes = request.grantTypes().stream()
        .map(AuthorizationGrantType::new)
        .collect(Collectors.toSet());

    RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(request.clientId())
        .clientSecret(request.clientSecret())
        .clientIdIssuedAt(Instant.now())
        .clientName(request.clientName())
        .clientAuthenticationMethods(methods -> methods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
        .authorizationGrantTypes(types -> types.addAll(grantTypes))
        .redirectUris(uris -> uris.addAll(request.redirectUris()))
        .scopes(scopes -> scopes.addAll(request.scopes()))
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
        .tokenSettings(tokenSettings)
        .build();

    registeredClientRepository.save(client);

    return new RegisteredClientResponse(client.getId(), client.getClientId(), client.getClientName());
  }

  public record RegisteredClientRequest(
      @NotBlank String clientId,
      @NotBlank String clientSecret,
      @NotBlank String clientName,
      @NotEmpty List<String> redirectUris,
      @NotEmpty List<String> grantTypes,
      @NotNull Set<String> scopes
  ) {}

  public record RegisteredClientResponse(String id, String clientId, String clientName) {}
}
