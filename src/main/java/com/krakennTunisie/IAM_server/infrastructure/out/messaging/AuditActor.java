package com.krakennTunisie.IAM_server.infrastructure.out.messaging;

import java.util.List;

public record AuditActor(
        String userId,
        String firstName,
        String lastName,
        List<String> roles
) {
}
