package com.krakennTunisie.IAM_server.infrastructure.out.persistence.mapper;

import com.krakennTunisie.IAM_server.domain.enums.UserStatus;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserProfileDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.RoleDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserDetailsDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserResponseDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.UserProfileEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProfileMapper {

    public UserProfileEntity toEntity(
            AddUserProfileDTO addUserProfileDTO
    ) {

        UserProfileEntity entity = new UserProfileEntity();

        entity.setKeycloakUserId(addUserProfileDTO.getKeycloakUserId());
        entity.setEmail(addUserProfileDTO.getAddUserDTO().getEmail());
        entity.setFirstName(addUserProfileDTO.getAddUserDTO().getFirstname());
        entity.setLastName(addUserProfileDTO.getAddUserDTO().getLastname());
        entity.setRole(addUserProfileDTO.getAddUserDTO().getRole());
        entity.setPhoneNumber(addUserProfileDTO.getAddUserDTO().getPhoneNumber());

        entity.setStatus(
                addUserProfileDTO.getAddUserDTO().getStatus() != null
                        ? UserStatus.valueOf(addUserProfileDTO.getAddUserDTO().getStatus())
                        : UserStatus.ACTIVE
        );

        return entity;
    }

    public UserResponseDTO toModel(
            UserProfileEntity entity,
            UserDto keycloakUser
    ) {

       return UserResponseDTO.builder()
                .idUser(entity.getIdUser())
                .keycloakUserId(entity.getKeycloakUserId())
                .username(keycloakUser.getUsername())
                .firstName(keycloakUser.getFirstName())
                .lastName(keycloakUser.getLastName())
                .email(keycloakUser.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .status(entity.getStatus())
                .enabled(keycloakUser.isEnabled())
                .roles(keycloakUser.getRoles())
                .createdAt(entity.getCreatedAt())
                .build();

    }

    public UserDetailsDTO toDetailsModel(
            UserProfileEntity entity,
            UserDto keycloakUser,
            List<RoleDTO> roles
    ) {

        return UserDetailsDTO.builder()
                .idUser(entity.getIdUser())
                .keycloakUserId(entity.getKeycloakUserId())
                .username(keycloakUser.getUsername())
                .firstName(keycloakUser.getFirstName())
                .lastName(keycloakUser.getLastName())
                .email(keycloakUser.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .status(entity.getStatus())
                .enabled(keycloakUser.isEnabled())
                .roles(roles)
                .createdAt(entity.getCreatedAt())
                .build();

    }
}
