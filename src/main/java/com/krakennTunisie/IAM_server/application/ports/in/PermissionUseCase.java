package com.krakennTunisie.IAM_server.application.ports.in;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.ClientPermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionAddDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionUpdateDTO;

public interface PermissionUseCase extends BaseUseCase<
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
