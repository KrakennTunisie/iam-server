package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.ClientPermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionAddDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionUpdateDTO;

public interface PermissionsRepositoryPort extends RepositoryPort<
        ClientPermissionDTO,
        PermissionDTO,
        PermissionAddDTO,
        PermissionUpdateDTO,
        String>{

    void createIfAbsent(
            String clientId,
            String permissionName,
            String description);
}
