package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientProperties {
    private String clientId;

    private List<PermissionProperties> permissions = new ArrayList<>();
}
