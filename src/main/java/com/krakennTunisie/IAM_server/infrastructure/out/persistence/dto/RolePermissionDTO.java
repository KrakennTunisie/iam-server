package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RolePermissionDTO {

    private String clientId;

    private String name;

    private String description;

}
