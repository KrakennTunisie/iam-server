package com.krakennTunisie.IAM_server.shared;

import com.krakennTunisie.IAM_server.application.ports.in.RoleUseCase;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config.SecurityPoliciesConfig;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.RoleAddDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.RolePermissionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleBootstrap {

    private final SecurityPoliciesConfig properties;
    private final RoleUseCase roleService;
    private final ApplicationEventPublisher publisher;

    @EventListener(PermissionPoliciesInitializedEvent.class)
    public void bootstrapAdminRole() {

        List<RolePermissionDTO> permissions = properties.getClients()
                .stream()
                .flatMap(client -> client.getPermissions().stream()
                        .map(permission ->
                                RolePermissionDTO
                                        .builder()
                                        .clientId(client.getClientId())
                                        .name(permission.getName())
                                        .description(permission.getDescription())
                                        .build()
                        )
                )
                .toList();

        RoleAddDTO adminRole = RoleAddDTO.builder()
                .name("Admin")
                .description("Full administrative access to all KERP clients")
                .permissionDTOList(permissions)
                .build();

        roleService.create(adminRole);
        publisher.publishEvent(new RoleInitializedEvent());
    }
}
