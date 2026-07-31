package com.krakennTunisie.IAM_server.application.service;

import com.krakennTunisie.IAM_server.application.ports.in.AuditLogUseCase;
import com.krakennTunisie.IAM_server.application.ports.out.AuditLogRepositoryPort;
import com.krakennTunisie.IAM_server.domain.model.AuditLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditLogService implements AuditLogUseCase {
    private final AuditLogRepositoryPort auditLogRepositoryPort;

    @Override
    public void save(AuditLog auditLog) {
        auditLogRepositoryPort.save(auditLog);
    }
}
