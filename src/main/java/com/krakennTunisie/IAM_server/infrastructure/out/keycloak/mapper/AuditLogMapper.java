package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper;

import com.krakennTunisie.IAM_server.domain.model.AuditLog;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.AuditLogEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogEntity toEntity(AuditLog auditLog){
        if(auditLog==null){
            return null;
        }

        AuditLogEntity auditLogEntity = new AuditLogEntity();
        auditLogEntity.setId(auditLog.getId());
        auditLogEntity.setAction(auditLog.getAction());
        auditLogEntity.setResource(auditLog.getResource());
        auditLogEntity.setResourceId(auditLog.getResourceId());
        auditLogEntity.setIpAddress(auditLog.getIpAddress());
        auditLogEntity.setDetails(auditLog.getDetails());
        auditLogEntity.setUserAgent(auditLog.getUserAgent());
        auditLogEntity.setUsername(auditLog.getUsername());
        auditLogEntity.setCreatedAt(auditLog.getCreatedAt());
        auditLogEntity.setKeycloakUserId(auditLog.getKeycloakUserId());
        auditLogEntity.setUpdatedAt(auditLog.getUpdatedAt());

        return auditLogEntity;
    }

    AuditLog toDomain(AuditLogEntity auditLogEntity){
        if(auditLogEntity==null){
            return null;
        }

        return AuditLog.builder()
                .id(auditLogEntity.getId())
                .keycloakUserId(auditLogEntity.getKeycloakUserId())
                .username(auditLogEntity.getUsername())
                .action(auditLogEntity.getAction())
                .resource(auditLogEntity.getResource())
                .resourceId(auditLogEntity.getResourceId())
                .details(auditLogEntity.getDetails())
                .ipAddress(auditLogEntity.getIpAddress())
                .userAgent(auditLogEntity.getUserAgent())
                .createdAt(auditLogEntity.getCreatedAt())
                .updatedAt(auditLogEntity.getUpdatedAt())
                .build();
    }
}
