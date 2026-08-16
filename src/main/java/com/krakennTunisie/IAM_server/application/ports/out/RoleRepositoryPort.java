package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.*;

import java.util.List;

public interface RoleRepositoryPort extends RepositoryPort<
        RoleDTO,
        RoleDTO,
        RoleAddDTO,
        UserRoleUpdateDTO,
        String>{

    RoleDTO addPermissions(String roleName, List<RolePermissionDTO> permissions);

    boolean existsByName(String roleName);

    RoleDTO revokePermission(String roleName, RolePermissionDTO permission);

    RoleDTO getRoleByName(String roleName);

    List<RoleSummaryDTO> getAllRoles();
}
