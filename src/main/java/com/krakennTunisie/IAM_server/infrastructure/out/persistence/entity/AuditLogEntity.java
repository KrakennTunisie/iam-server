package com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class AuditLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idAuditLog ;
}
