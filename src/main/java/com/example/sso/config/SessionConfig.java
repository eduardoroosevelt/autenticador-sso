package com.example.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration
public class SessionConfig {
  @Bean
  public RedisConnectionFactory redisConnectionFactory(
      @Value("${spring.data.redis.host}") String host,
      @Value("${spring.data.redis.port}") int port
  ) {
    return new LettuceConnectionFactory(host, port);
  }

  @Bean
  public FindByIndexNameSessionRepository<? extends Session> sessionRepository(
      RedisConnectionFactory connectionFactory
  ) {
    return new RedisIndexedSessionRepository(connectionFactory);
  }

  @Bean
  public SessionRepositoryFilter<? extends Session> springSessionRepositoryFilter(
      FindByIndexNameSessionRepository<? extends Session> sessionRepository
  ) {
    return new SessionRepositoryFilter<>(sessionRepository);
  }
}
