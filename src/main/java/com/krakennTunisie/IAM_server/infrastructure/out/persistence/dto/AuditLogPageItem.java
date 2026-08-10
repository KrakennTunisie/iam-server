package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import com.krakennTunisie.IAM_server.domain.enums.AuditAction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class AuditLogPageItem {

    private UUID id;

    private String keycloakUserId;

    private String username;

    @Enumerated(EnumType.STRING)
    private AuditAction action;

    private String resource;

    private String resourceId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
