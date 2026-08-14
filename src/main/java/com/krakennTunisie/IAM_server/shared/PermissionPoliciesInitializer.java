package com.krakennTunisie.IAM_server.shared;

import com.krakennTunisie.IAM_server.application.ports.in.ClientUseCase;
import com.krakennTunisie.IAM_server.application.ports.in.PermissionUseCase;
import com.krakennTunisie.IAM_server.domain.enums.BillingPermission;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config.ClientProperties;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config.PermissionProperties;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config.SecurityPoliciesConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class PermissionPoliciesInitializer {

    private final PermissionUseCase permissionUseCase;
    private final ClientUseCase clientUseCase;
    private final SecurityPoliciesConfig properties;
    private final ApplicationEventPublisher publisher;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {

        log.info("Synchronizing Keycloak permissions...");

        for (ClientProperties client : properties.getClients()) {

            clientUseCase.createIfAbsent(client.getClientId());
            log.info("Client {} synchronized", client.getClientId());

            log.info("Processing client {}", client.getClientId());

            for (PermissionProperties permission : client.getPermissions()) {

                try {

                    permissionUseCase.createIfAbsent(
                            client.getClientId(),
                            permission.getName(),
                            permission.getDescription());

                } catch (Exception e) {

                    log.error(
                            "Unable to create permission {} for client {}",
                            permission.getName(),
                            client.getClientId(),
                            e);

                }

            }

        }

        log.info("Keycloak permissions synchronized.");
        publisher.publishEvent(new PermissionPoliciesInitializedEvent());

    }

}