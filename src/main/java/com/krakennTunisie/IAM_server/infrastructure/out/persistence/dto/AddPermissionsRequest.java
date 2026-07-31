package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import java.util.List;

public record AddPermissionsRequest(
        List<RolePermissionDTO> permissions
) {
}
