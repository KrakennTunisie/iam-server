package com.krakennTunisie.IAM_server.application.service;

import com.krakennTunisie.IAM_server.application.ports.in.PermissionUseCase;
import com.krakennTunisie.IAM_server.application.ports.out.PermissionsRepositoryPort;
import com.krakennTunisie.IAM_server.domain.enums.BillingPermission;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.ClientPermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionAddDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PermissionService implements PermissionUseCase {

    private final PermissionsRepositoryPort permissionsRepositoryPort;

    @Override
    public PermissionDTO create(PermissionAddDTO request) {
        return null;
    }

    @Override
    public PermissionDTO update(String s, PermissionUpdateDTO request) {
        return null;
    }

    @Override
    public PermissionDTO get(String s) {
        return null;
    }

    @Override
    public Page<ClientPermissionDTO> getAll(String keyword, String filter, int page, int size) {
        return permissionsRepositoryPort.getAll(keyword, filter, page, size);
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void createIfAbsent(
            String clientId,
            String permissionName,
            String description) {
        permissionsRepositoryPort.createIfAbsent(clientId, permissionName, description);
    }
}
