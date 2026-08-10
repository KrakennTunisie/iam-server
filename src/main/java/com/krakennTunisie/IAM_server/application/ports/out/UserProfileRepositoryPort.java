package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserDetailsDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserResponseDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;
import org.springframework.data.domain.Page;

public interface UserProfileRepositoryPort extends RepositoryPort<
        UserResponseDTO,
        UserResponseDTO,
        AddUserDTO,
        UpdateUserRequest,
        String
        >{

    Page<UserResponseDTO> getAllUsers(String keyword, String statusFilter, String roleFilter, int page, int size);

    UserDetailsDTO getUserDetails(String idUser);

    void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO);

    boolean existsById(String idUser);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, String idUser);

    boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, String idUser);


    void enable(String userId);

    void disable(String userId);
}
