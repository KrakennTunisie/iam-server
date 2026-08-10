package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.domain.model.AuditLog;
import org.springframework.data.domain.Page;

public interface AuditLogRepositoryPort {
    void save(AuditLog log);
/*    RESPONSE get(ID id);

    Page<RESPONSE> getAll(String keyword, String filter, int page, int size);*/
}
