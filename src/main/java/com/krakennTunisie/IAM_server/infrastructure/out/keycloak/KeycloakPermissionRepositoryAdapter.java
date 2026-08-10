package com.krakennTunisie.IAM_server.infrastructure.out.keycloak;

import com.krakennTunisie.IAM_server.application.ports.out.PermissionsRepositoryPort;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config.SecurityPoliciesConfig;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper.KeycloakMapper;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.ClientPermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionAddDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakPermissionRepositoryAdapter implements PermissionsRepositoryPort {

    private final KeycloakMapper keycloakMapper;

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    private final SecurityPoliciesConfig securityPoliciesConfig;


    private RealmResource realm() {
        return keycloak.realm(realm);
    }



    @Override
    public void createIfAbsent(
            String clientId,
            String permissionName,
            String description) {

        RolesResource roles = client(clientId).roles();

        boolean exists = roles.list()
                .stream()
                .anyMatch(role ->
                        role.getName().equals(permissionName));

        if (exists) {
            return;
        }

        RoleRepresentation role = new RoleRepresentation();

        role.setName(permissionName);
        role.setDescription(description);

        roles.create(role);
    }



    @Override
    public PermissionDTO create(PermissionAddDTO request) {
        return null;
    }

    @Override
    public PermissionDTO update(String s, PermissionUpdateDTO request) {
        return null;
    }

    @Override
    public PermissionDTO get(String s) {
        return null;
    }

    @Override
    public Page<ClientPermissionDTO> getAll(String keyword, String filter, int page, int size) {

        List<ClientPermissionDTO> clients = securityPoliciesConfig.getClients()
                .stream()

                // Optional keyword filter
                .filter(client ->
                        keyword == null
                                || keyword.isBlank()
                                || client.getClientId().toLowerCase().contains(keyword.toLowerCase()))

                .map(configClient -> {

                    ClientRepresentation client = realm()
                            .clients()
                            .findByClientId(configClient.getClientId())
                            .stream()
                            .findFirst()
                            .orElseThrow(() ->
                                    new IllegalStateException(
                                            "Client '%s' not found in Keycloak"
                                                    .formatted(configClient.getClientId())));

                    List<PermissionDTO> permissions = realm()
                            .clients()
                            .get(client.getId())
                            .roles()
                            .list()
                            .stream()
                            .map(keycloakMapper::permissionToDTO)
                            .toList();

                    return ClientPermissionDTO.builder()
                            .clientId(client.getClientId())
                            .permissions(permissions)
                            .build();

                })
                .toList();

        int start = Math.min(page * size, clients.size());
        int end = Math.min(start + size, clients.size());

        return new PageImpl<>(
                clients.subList(start, end),
                PageRequest.of(page, size),
                clients.size()
        );
    }

    @Override
    public void delete(String s) {

    }


    private ClientResource client(String clientId) {

        ClientRepresentation client = realm()
                .clients()
                .findByClientId(clientId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Client '%s' not found".formatted(clientId)));

        return realm()
                .clients()
                .get(client.getId());
    }
}
