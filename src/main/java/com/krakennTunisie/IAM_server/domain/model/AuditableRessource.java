package com.krakennTunisie.IAM_server.domain.model;

public interface AuditableRessource {

        String getAuditId();

        String getAuditLabel();

        String getAuditType();
}
