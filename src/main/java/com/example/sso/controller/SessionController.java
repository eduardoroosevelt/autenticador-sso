package com.example.sso.controller;

import com.example.sso.service.SessionManagementService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
@Validated
public class SessionController {
  private final SessionManagementService sessionManagementService;

  public SessionController(SessionManagementService sessionManagementService) {
    this.sessionManagementService = sessionManagementService;
  }

  @DeleteMapping("/{username}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void invalidateSessions(@PathVariable @NotBlank String username) {
    sessionManagementService.invalidateSessionsByUsername(username);
  }
}
