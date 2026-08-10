package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;

import java.util.List;

public interface  UserRepositoryPort extends RepositoryPort<
        UserDto,
        UserDto,
        AddUserDTO,
        UpdateUserRequest,
        String> {

    void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdNot(String username, String idUser);

    boolean existsByEmailAndIdNot(String email, String idUser);

    boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, String idUser);


    void resetPassword(String email, String password);

    void assignRoles(String userId, List<String> roles);

    void removeRoles(String userId, List<String> roles);

    void enable(String userId);

    void disable(String userId);
}
