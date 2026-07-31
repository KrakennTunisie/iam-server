package com.krakennTunisie.IAM_server.application.ports.in;

import com.krakennTunisie.IAM_server.domain.model.AuditLog;

public interface AuditLogUseCase {
    void save(AuditLog auditLog);
}
