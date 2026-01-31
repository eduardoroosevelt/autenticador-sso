package com.example.sso.controller;

import com.example.sso.repository.OAuth2ClientRegistrationRepository;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  private final OAuth2ClientRegistrationRepository registrationRepository;

  public LoginController(OAuth2ClientRegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
  }

  @GetMapping("/login")
  public String login(Model model) {
    Map<String, String> socialProviders = registrationRepository.findAll().stream()
        .collect(Collectors.toMap(
            registration -> registration.getRegistrationId(),
            registration -> registration.getClientName()
        ));
    model.addAttribute("socialProviders", socialProviders);
    return "login";
  }
}
