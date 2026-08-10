package com.krakennTunisie.IAM_server.application.ports.in;

import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserDetailsDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserResponseDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;
import org.springframework.data.domain.Page;

public interface UserProfileUseCase extends BaseUseCase<
        UserResponseDTO,
        UserResponseDTO,
        AddUserDTO,
        UpdateUserRequest,
        String>{

    Page<UserResponseDTO> getAllUsers(String keyword, String statusFilter, String roleFilter, int page, int size);

    UserDetailsDTO getUserDetails(String userId);

    void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO);

    boolean existsByEmailAndIdNot(String email, String idUser);

    void enable(String userId);

    void disable(String userId);
}
