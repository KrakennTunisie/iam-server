package com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity;

import com.krakennTunisie.IAM_server.domain.enums.AuditAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String keycloakUserId;

    private String username;

    @Enumerated(EnumType.STRING)
    private AuditAction action;

    private String resource;

    private String resourceId;

    @Column(length = 2000)
    private String details;

    private String ipAddress;

    private String userAgent;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
