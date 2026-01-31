package com.example.sso.service;

import java.util.Map;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

@Service
public class SessionManagementService {
  private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

  public SessionManagementService(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public int invalidateSessionsByUsername(String username) {
    Map<String, ? extends Session> sessions = sessionRepository.findByPrincipalName(username);
    sessions.keySet().forEach(sessionRepository::deleteById);
    return sessions.size();
  }
}
