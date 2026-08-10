package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import com.krakennTunisie.IAM_server.domain.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class UserResponseDTO {

    private UUID idUser;
    private String keycloakUserId;

    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private String phoneNumber;
    private UserStatus status;

    private boolean enabled;
    private LocalDateTime createdAt;
    private List<String> roles;
}