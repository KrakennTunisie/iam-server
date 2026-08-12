package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.messaging.AuditEvent;

public interface AuditEventPublisherPort {
    void publish(AuditEvent auditEvent);
}
