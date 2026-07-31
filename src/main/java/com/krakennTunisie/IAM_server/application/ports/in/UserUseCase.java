package com.krakennTunisie.IAM_server.application.ports.in;

import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;

import java.util.List;

public interface UserUseCase extends BaseUseCase<
        UserDto,
        UserDto,
        AddUserDTO,
        UpdateUserRequest,
        String> {

    void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void resetPassword(String userId, String password);

    void assignRoles(String userId, List<String> roles);

    void removeRoles(String userId, List<String> roles);

    void enable(String userId);

    void disable(String userId);
}
