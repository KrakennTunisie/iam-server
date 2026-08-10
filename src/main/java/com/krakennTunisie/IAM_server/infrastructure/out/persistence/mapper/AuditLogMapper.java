package com.krakennTunisie.IAM_server.infrastructure.out.persistence.mapper;

import com.krakennTunisie.IAM_server.domain.model.AuditLog;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.AuditLogPageItem;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.AuditLogEntity;

public class AuditLogMapper {

    AuditLogEntity toEntity(AuditLog auditLog){
        if(auditLog==null){
            return null;
        }

        AuditLogEntity auditLogEntity = new AuditLogEntity();

        auditLogEntity.setId(auditLog.getId());
        auditLogEntity.setResourceId(auditLog.getResourceId());
        auditLogEntity.setResource(auditLog.getResource());
        auditLogEntity.setUsername(auditLog.getUsername());
        auditLogEntity.setKeycloakUserId(auditLog.getKeycloakUserId());
        auditLogEntity.setIpAddress(auditLog.getIpAddress());
        auditLogEntity.setDetails(auditLog.getDetails());
        auditLogEntity.setAction(auditLog.getAction());
        auditLogEntity.setUserAgent(auditLog.getUserAgent());
        auditLogEntity.setCreatedAt(auditLog.getCreatedAt());
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

    AuditLogPageItem toPageItem(AuditLog auditLog){
        if(auditLog==null){
            return null;
        }

        return AuditLogPageItem.builder()
                .id(auditLog.getId())
                .keycloakUserId(auditLog.getKeycloakUserId())
                .username(auditLog.getUsername())
                .action(auditLog.getAction())
                .resource(auditLog.getResource())
                .resourceId(auditLog.getResourceId())
                .createdAt(auditLog.getCreatedAt())
                .updatedAt(auditLog.getUpdatedAt())
                .build();
    }


}
