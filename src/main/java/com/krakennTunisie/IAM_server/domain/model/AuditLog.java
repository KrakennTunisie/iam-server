package com.krakennTunisie.IAM_server.domain.model;

import com.krakennTunisie.IAM_server.domain.enums.AuditAction;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {


    private UUID id;

    private String keycloakUserId;

    private String username;

    @Enumerated(EnumType.STRING)
    private AuditAction action;

    private String resource;

    private String resourceId;

    private String details;

    private String ipAddress;

    private String userAgent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
