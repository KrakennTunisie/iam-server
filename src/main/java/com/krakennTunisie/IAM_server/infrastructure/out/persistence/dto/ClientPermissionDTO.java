package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ClientPermissionDTO {
    private String clientId;

    private List<PermissionDTO> permissions;
}
