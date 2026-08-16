package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionProperties {
    private String name;

    private String description;
}
