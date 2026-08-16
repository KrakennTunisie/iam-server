package com.krakennTunisie.IAM_server.shared;

import com.krakennTunisie.IAM_server.infrastructure.out.messaging.AuditEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuditEventFactory {

    private static final String SOURCE_SERVICE = "iam-service";

    //private final AuditActorProvider auditActorProvider;

    public AuditEvent passwordChanged(
            UUID correlationId,
            String userId,
            String ipAddress,
            boolean success,
            String failureReason
    ) {

        return base(
                correlationId,
                "PASSWORD_CHANGED",
                "User",
                userId,
                null,
                null,
                success,
                failureReason,
                ipAddress,
                null
        );
    }

    public AuditEvent userCreated(
            UUID correlationId,
            String userId,
            Map<String, Object> after,
            String ipAddress,
            Long enversRevision
    ) {

        return base(
                correlationId,
                "USER_CREATED",
                "User",
                userId,
                null,
                after,
                true,
                null,
                ipAddress,
                enversRevision
        );
    }

    public AuditEvent userUpdated(
            UUID correlationId,
            String userId,
            Map<String, Object> before,
            Map<String, Object> after,
            String ipAddress,
            Long enversRevision
    ) {

        return base(
                correlationId,
                "USER_UPDATED",
                "User",
                userId,
                before,
                after,
                true,
                null,
                ipAddress,
                enversRevision
        );
    }

    public AuditEvent userRoleAssigned(
            UUID correlationId,
            String userId,
            String role,
            String ipAddress
    ) {

        return base(
                correlationId,
                "USER_ROLE_ASSIGNED",
                "User",
                userId,
                null,
                Map.of("assignedRole", role),
                true,
                null,
                ipAddress,
                null
        );
    }

    public AuditEvent userRoleRevoked(
            UUID correlationId,
            String userId,
            String role,
            String ipAddress
    ) {

        return base(
                correlationId,
                "USER_ROLE_REVOKED",
                "User",
                userId,
                Map.of("revokedRole", role),
                null,
                true,
                null,
                ipAddress,
                null
        );
    }
    public AuditEvent userDisabled(
            UUID correlationId,
            String userId,
            String ipAddress
    ) {

        return base(
                correlationId,
                "USER_DISABLED",
                "User",
                userId,
                Map.of("enabled", true),
                Map.of("enabled", false),
                true,
                null,
                ipAddress,
                null
        );
    }

    public AuditEvent userEnabled(
            UUID correlationId,
            String userId,
            String ipAddress
    ) {

        return base(
                correlationId,
                "USER_ENABLED",
                "User",
                userId,
                Map.of("enabled", false),
                Map.of("enabled", true),
                true,
                null,
                ipAddress,
                null
        );
    }

    private AuditEvent base(
            UUID correlationId,
            String action,
            String resourceType,
            String resourceId,
            Map<String, Object> before,
            Map<String, Object> after,
            boolean success,
            String failureReason,
            String ipAddress,
            Long enversRevision
    ) {

        //AuditActor auditActor = auditActorProvider.getCurrentActor();

        return new AuditEvent(
                UUID.randomUUID(),
                correlationId,
                Instant.now(),
                SOURCE_SERVICE,
                /*auditActor.userId()*/"714165ba-4115-45f2-a5d4-6240d521c6f3",
                /*auditActor.firstName()*/ "Wassef",
                /*auditActor.lastName()*/"Ammar",
                /*auditActor.roles()*/List.of("Admin"),
                action,
                resourceType,
                resourceId,
                before,
                after,
                success ? "SUCCESS" : "FAILURE",
                failureReason,
                ipAddress,
                enversRevision
        );
    }
}
