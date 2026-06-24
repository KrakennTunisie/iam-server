package com.krakennTunisie.IAM_server.infrastructure.out.persistence.repository;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {
}
