package com.krakennTunisie.IAM_server.infrastructure.out.keycloak;

import com.krakennTunisie.IAM_server.application.ports.out.RoleRepositoryPort;
import com.krakennTunisie.IAM_server.domain.exception.IAMException;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper.KeycloakMapper;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.*;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class KeycloakRoleRepositoryAdapter implements RoleRepositoryPort {

    private final KeycloakMapper keycloakMapper;

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    private RealmResource realm() {
        return keycloak.realm(realm);
    }

    private RolesResource rolesResource() {
        return realm().roles();
    }

    @Override
    public RoleDTO create(RoleAddDTO request) {
        RolesResource roles = realm().roles();

        boolean exists = roles.list()
                .stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(request.getName()));

        if (exists) {
            throw IAMException.alreadyExists("Rôle", "nom", request.getName());
        }

        // Create realm role
        RoleRepresentation role = new RoleRepresentation();

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        roles.create(role);

        // Retrieve created role
        RoleResource roleResource = roles.get(request.getName());

        // Assign client permissions
        List<RoleRepresentation> composites = new ArrayList<>();

        for (RolePermissionDTO permission : request.getPermissionDTOList()) {

            ClientRepresentation client = realm()
                    .clients()
                    .findByClientId(permission.getClientId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() ->
                            IAMException.notFound(
                                    "Catégorie",
                                    "idClient"
                                    , permission.getClientId()));

            RoleRepresentation clientRole = realm()
                    .clients()
                    .get(client.getId())
                    .roles()
                    .get(permission.getName())
                    .toRepresentation();

            composites.add(clientRole);
        }

        if (!composites.isEmpty()) {
            roleResource.addComposites(composites);
        }

        RoleRepresentation createdRole = roleResource.toRepresentation();

        return RoleDTO.builder()
                .name(createdRole.getName())
                .description(createdRole.getDescription())
                .permissions(request.getPermissionDTOList())
                .build();
    }

    @Override
    public RoleDTO update(String s, UserRoleUpdateDTO request) {
        return null;
    }

    @Override
    public RoleDTO get(String s) {
        return null;
    }

    @Override
    public Page<RoleDTO> getAll(String keyword, String filter, int page, int size) {
        // Cache clientId lookup once
        Map<String, String> clientMap = realm()
                .clients()
                .findAll()
                .stream()
                .collect(Collectors.toMap(
                        ClientRepresentation::getId,
                        ClientRepresentation::getClientId
                ));

        Stream<RoleRepresentation> stream = realm()
                .roles()
                .list()
                .stream()
                .filter(role -> !isSystemRole(role.getName()));

        if (keyword != null && !keyword.isBlank()) {

            String search = keyword.toLowerCase();

            stream = stream.filter(role ->
                    role.getName().toLowerCase().contains(search)
                            || Optional.ofNullable(role.getDescription())
                            .orElse("")
                            .toLowerCase()
                            .contains(search));
        }

        List<RoleDTO> roles = stream
                .map(role -> toRoleDTO(role, clientMap))
                .toList();

        int from = Math.min(page * size, roles.size());
        int to = Math.min(from + size, roles.size());

        return new PageImpl<>(
                roles.subList(from, to),
                PageRequest.of(page, size),
                roles.size()
        );
    }

    @Override
    public void delete(String s) {

    }


    private boolean isSystemRole(String roleName) {

        return roleName.startsWith("default-roles-")
                || roleName.equals("offline_access")
                || roleName.equals("uma_authorization");
    }



    @Override
    public RoleDTO addPermissions(String roleName, List<RolePermissionDTO> permissions) {
        RoleResource role = realm()
                .roles()
                .get(roleName);

        RoleRepresentation roleRepresentation = role.toRepresentation();

        // Cache all clients
        Map<String, ClientRepresentation> clients = realm()
                .clients()
                .findAll()
                .stream()
                .collect(Collectors.toMap(
                        ClientRepresentation::getClientId,
                        Function.identity()
                ));

        // Existing permissions
        Set<String> existingPermissions = role.getRoleComposites()
                .stream()
                .map(r -> r.getContainerId() + ":" + r.getName())
                .collect(Collectors.toSet());

        List<RoleRepresentation> composites = new ArrayList<>();

        for (RolePermissionDTO permission : permissions) {

            ClientRepresentation client = clients.get(permission.getClientId());

            if (client == null) {
                throw IAMException.notFound("Permission",
                        "nom",
                        permission.getName());
            }

            RoleRepresentation clientRole = realm()
                    .clients()
                    .get(client.getId())
                    .roles()
                    .get(permission.getName())
                    .toRepresentation();

            String key = clientRole.getContainerId() + ":" + clientRole.getName();

            if (!existingPermissions.contains(key)) {
                composites.add(clientRole);
            }
        }

        if (!composites.isEmpty()) {
            role.addComposites(composites);
        }

        Map<String, String> clientMap = clients.values()
                .stream()
                .collect(Collectors.toMap(
                        ClientRepresentation::getId,
                        ClientRepresentation::getClientId
                ));

        return toRoleDTO(roleRepresentation, clientMap);

    }

    @Override
    public RoleDTO revokePermission(String roleName, RolePermissionDTO permission) {
        RoleResource roleResource = realm()
                .roles()
                .get(roleName);

        ClientRepresentation client = realm()
                .clients()
                .findByClientId(permission.getClientId())
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Client not found: " + permission.getClientId()
                        )
                );

        RoleRepresentation clientRole = realm()
                .clients()
                .get(client.getId())
                .roles()
                .get(permission.getName())
                .toRepresentation();


        roleResource.deleteComposites(
                List.of(clientRole)
        );

        return getRoleByName(roleName);

    }

    @Override
    public RoleDTO getRoleByName(String roleName) {

        RoleRepresentation role;

        try {
            role = rolesResource()
                    .get(roleName)
                    .toRepresentation();

        } catch (NotFoundException e) {
            throw IAMException.notFound("Rôle","nom",roleName);
        }

        Set<RolePermissionDTO> permissions = getRolePermissions(roleName);

        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(new ArrayList<>(permissions))
                .build();
    }

    @Override
    public List<RoleSummaryDTO> getAllRoles() {
        return rolesResource().list()
                .stream()
                .filter(roleRepresentation -> !isSystemRole(roleRepresentation.getName()))
                .map(role -> new RoleSummaryDTO(
                        role.getId(),
                        role.getName()
                ))
                .toList();
    }

    private Set<RolePermissionDTO> getRolePermissions(String roleName) {

        RoleRepresentation role = realm()
                .roles()
                .get(roleName)
                .toRepresentation();

        Set<RoleRepresentation> composites = realm()
                .roles()
                .get(roleName)
                .getRoleComposites();

        return composites.stream()
                .map(composite -> {

                    ClientRepresentation client = realm()
                            .clients()
                            .get(composite.getContainerId())
                            .toRepresentation();

                    return RolePermissionDTO.builder()
                            .clientId(client.getClientId())
                            .name(composite.getName())
                            .description(composite.getDescription())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    public RoleDTO toRoleDTO(RoleRepresentation roleRepresentation, Map<String, String> clientMap) {
        if (roleRepresentation == null) {
            return null;
        }
        RoleResource roleResource = realm()
                .roles()
                .get(roleRepresentation.getName());

        List<RolePermissionDTO> permissions = roleResource
                .getRoleComposites()
                .stream()
                .map(permission -> toPermissionDTO(permission, clientMap))
                .toList();

        return RoleDTO.builder()
                .id(roleRepresentation.getId())
                .name(roleRepresentation.getName())
                .description(roleRepresentation.getDescription())
                .permissions(permissions)
                .build();

    }

    private RolePermissionDTO toPermissionDTO(
            RoleRepresentation permission,
            Map<String, String> clientMap
    ) {

        return RolePermissionDTO.builder()
                .clientId(clientMap.get(permission.getContainerId()))
                .name(permission.getName())
                .description(permission.getDescription())
                .build();

    }
}
