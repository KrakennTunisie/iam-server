package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PermissionDTO {
    private String id;
    private String name;
    private String description;
}
