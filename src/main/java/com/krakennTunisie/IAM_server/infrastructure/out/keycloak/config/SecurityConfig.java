package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityPoliciesConfig.class)
public class SecurityConfig {
}
