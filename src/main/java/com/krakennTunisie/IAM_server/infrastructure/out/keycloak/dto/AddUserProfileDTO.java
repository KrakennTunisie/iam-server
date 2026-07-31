package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddUserProfileDTO {
    private String keycloakUserId;
    private AddUserDTO addUserDTO;
}
