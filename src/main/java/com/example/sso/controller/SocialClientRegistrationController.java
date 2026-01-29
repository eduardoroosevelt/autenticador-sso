package com.example.sso.controller;

import com.example.sso.model.OAuth2ClientRegistrationEntity;
import com.example.sso.repository.OAuth2ClientRegistrationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/social-clients")
@Validated
public class SocialClientRegistrationController {
  private final OAuth2ClientRegistrationRepository repository;

  public SocialClientRegistrationController(OAuth2ClientRegistrationRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OAuth2ClientRegistrationEntity register(@Valid @RequestBody SocialClientRegistrationRequest request) {
    OAuth2ClientRegistrationEntity entity = new OAuth2ClientRegistrationEntity();
    entity.setRegistrationId(request.registrationId());
    entity.setClientId(request.clientId());
    entity.setClientSecret(request.clientSecret());
    entity.setScopes(request.scopes());
    entity.setAuthorizationUri(request.authorizationUri());
    entity.setTokenUri(request.tokenUri());
    entity.setUserInfoUri(request.userInfoUri());
    entity.setUserNameAttribute(request.userNameAttribute());
    entity.setIssuerUri(request.issuerUri());
    entity.setClientName(request.clientName());
    return repository.save(entity);
  }

  public record SocialClientRegistrationRequest(
      @NotBlank String registrationId,
      @NotBlank String clientId,
      @NotBlank String clientSecret,
      String scopes,
      @NotBlank String authorizationUri,
      @NotBlank String tokenUri,
      @NotBlank String userInfoUri,
      @NotBlank String userNameAttribute,
      String issuerUri,
      @NotBlank String clientName
  ) {}
}
