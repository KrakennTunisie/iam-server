package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "kerp.security")
public class SecurityPoliciesConfig {
    private List<ClientProperties> clients = new ArrayList<>();

}
