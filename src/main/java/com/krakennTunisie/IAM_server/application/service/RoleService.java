package com.krakennTunisie.IAM_server.application.service;

import com.krakennTunisie.IAM_server.application.ports.in.RoleUseCase;
import com.krakennTunisie.IAM_server.application.ports.out.RoleRepositoryPort;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService implements RoleUseCase {

    private final RoleRepositoryPort roleRepositoryPort;
    @Override
    public RoleDTO create(RoleAddDTO request) {
        return roleRepositoryPort.create(request);
    }

    @Override
    public RoleDTO update(String s, UserRoleUpdateDTO request) {
        return null;
    }

    @Override
    public RoleDTO get(String s) {
        return null;
    }

    @Override
    public Page<RoleDTO> getAll(String keyword, String filter, int page, int size) {
        return roleRepositoryPort.getAll(keyword, filter, page, size);
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public RoleDTO addPermissions(String roleName, List<RolePermissionDTO> permissions) {
        return roleRepositoryPort.addPermissions(roleName, permissions);
    }

    @Override
    public boolean existsByName(String roleName) {
        return roleRepositoryPort.existsByName(roleName);
    }

    @Override
    public RoleDTO getRoleByName(String roleName) {
        return roleRepositoryPort.getRoleByName(roleName);
    }

    @Override
    public RoleDTO revokePermission(String roleName, RolePermissionDTO permission) {
        return roleRepositoryPort.revokePermission(roleName, permission);
    }

    @Override
    public List<RoleSummaryDTO> getAllRoles() {
        return roleRepositoryPort.getAllRoles();
    }
}
