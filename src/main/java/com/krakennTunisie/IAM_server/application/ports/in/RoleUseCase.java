package com.krakennTunisie.IAM_server.application.ports.in;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.*;

import java.util.List;

public interface RoleUseCase extends BaseUseCase<
        RoleDTO,
        RoleDTO,
        RoleAddDTO,
        UserRoleUpdateDTO,
        String>{
    RoleDTO addPermissions(String roleName, List<RolePermissionDTO> permissions);
    boolean existsByName(String roleName);
    RoleDTO getRoleByName(String roleName);
    RoleDTO revokePermission(String roleName, RolePermissionDTO permission);
    List<RoleSummaryDTO> getAllRoles();
}
