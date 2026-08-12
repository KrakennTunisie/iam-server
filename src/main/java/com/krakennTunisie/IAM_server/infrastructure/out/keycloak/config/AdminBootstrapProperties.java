package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kerp.bootstrap.admin")
@Data
public class AdminBootstrapProperties {

    private boolean enabled;

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String password;
    private String status;
    private String role;
}
