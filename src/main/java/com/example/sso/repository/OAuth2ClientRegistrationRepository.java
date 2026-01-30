package com.example.sso.repository;

import com.example.sso.model.OAuth2ClientRegistrationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2ClientRegistrationRepository extends JpaRepository<OAuth2ClientRegistrationEntity, Long> {
  Optional<OAuth2ClientRegistrationEntity> findByRegistrationId(String registrationId);
}
