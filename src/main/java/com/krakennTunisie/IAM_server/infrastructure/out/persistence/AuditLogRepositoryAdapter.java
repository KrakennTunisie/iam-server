package com.krakennTunisie.IAM_server.infrastructure.out.persistence;

import com.krakennTunisie.IAM_server.application.ports.out.AuditLogRepositoryPort;
import com.krakennTunisie.IAM_server.domain.model.AuditLog;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper.AuditLogMapper;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.repository.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuditLogRepositoryAdapter implements AuditLogRepositoryPort {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;
    @Override
    public void save(AuditLog log) {
        auditLogRepository.save(auditLogMapper.toEntity(log));
    }
}
