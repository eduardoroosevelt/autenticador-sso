package com.example.sso.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "oauth2_client_registration")
public class OAuth2ClientRegistrationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "registration_id", nullable = false, unique = true)
  private String registrationId;

  @Column(name = "client_id", nullable = false)
  private String clientId;

  @Column(name = "client_secret", nullable = false)
  private String clientSecret;

  @Column(name = "scopes")
  private String scopes;

  @Column(name = "authorization_uri", nullable = false)
  private String authorizationUri;

  @Column(name = "token_uri", nullable = false)
  private String tokenUri;

  @Column(name = "user_info_uri", nullable = false)
  private String userInfoUri;

  @Column(name = "user_name_attribute", nullable = false)
  private String userNameAttribute;

  @Column(name = "issuer_uri")
  private String issuerUri;

  @Column(name = "client_name", nullable = false)
  private String clientName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public void setRegistrationId(String registrationId) {
    this.registrationId = registrationId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getScopes() {
    return scopes;
  }

  public void setScopes(String scopes) {
    this.scopes = scopes;
  }

  public String getAuthorizationUri() {
    return authorizationUri;
  }

  public void setAuthorizationUri(String authorizationUri) {
    this.authorizationUri = authorizationUri;
  }

  public String getTokenUri() {
    return tokenUri;
  }

  public void setTokenUri(String tokenUri) {
    this.tokenUri = tokenUri;
  }

  public String getUserInfoUri() {
    return userInfoUri;
  }

  public void setUserInfoUri(String userInfoUri) {
    this.userInfoUri = userInfoUri;
  }

  public String getUserNameAttribute() {
    return userNameAttribute;
  }

  public void setUserNameAttribute(String userNameAttribute) {
    this.userNameAttribute = userNameAttribute;
  }

  public String getIssuerUri() {
    return issuerUri;
  }

  public void setIssuerUri(String issuerUri) {
    this.issuerUri = issuerUri;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }
}
