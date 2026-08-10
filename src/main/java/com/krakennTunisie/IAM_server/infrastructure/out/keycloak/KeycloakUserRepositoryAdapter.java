package com.krakennTunisie.IAM_server.infrastructure.out.keycloak;

import com.krakennTunisie.IAM_server.application.ports.out.RoleRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.UserRepositoryPort;
import com.krakennTunisie.IAM_server.domain.exception.IAMException;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper.KeycloakMapper;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;
import com.krakennTunisie.IAM_server.shared.ExtractErrorResponse;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class KeycloakUserRepositoryAdapter  implements UserRepositoryPort {

    private final KeycloakMapper keycloakMapper;

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    private RealmResource realm() {
        return keycloak.realm(realm);
    }

    private UsersResource usersResource() {
        return realm().users();
    }

    private RolesResource rolesResource(){return realm().roles();}

    private Set<String> systemRoles;

    @PostConstruct
    private void initSystemRoles() {
        this.systemRoles = Set.of(
                "default-roles-" + realm,
                "offline_access",
                "uma_authorization",
                "uma_protection"
        );
    }

    @Override
    public UserDto create(AddUserDTO dto) {

        UserRepresentation representation = keycloakMapper.toRepresentation(dto);

        try (Response response = usersResource().create(representation)) {

            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new RuntimeException("Unable to create user : " + ExtractErrorResponse.extractKeycloakError(response));
            }

            String id = CreatedResponseUtil.getCreatedId(response);

            assignRealmRole(id, dto.getRole());

            return get(id);

        }
    }

    private void assignRealmRole(String userId, String roleName) {

        RoleRepresentation role = realm()
                .roles()
                .get(roleName)
                .toRepresentation();

        realm()
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    @Override
    public UserDto update(String s, UpdateUserRequest request) {

            UserResource userResource = usersResource().get(s);

            UserRepresentation user = userResource.toRepresentation();

            if(existsByEmailAndIdNot(request.getEmail(), s)){
                throw IAMException.alreadyExists("Utilisateur", "email", request.getEmail());
            }

            if(existsByFirstNameAndLastNameAndIdNot(request.getFirstname(), request.getLastname(), s)){
                throw IAMException.alreadyExists("Utilisateur", "Nom et Prénom", request.getFirstname()+" "+request.getLastname());
            }


            // 2. Update standard Keycloak fields
            user.setFirstName(request.getFirstname());


            user.setLastName(request.getLastname());

            user.setEmail(request.getEmail());


            // 4. Update Keycloak user
            userResource.update(user);


            // 5. Return updated user
            UserRepresentation updatedUser = userResource.toRepresentation();

            return keycloakMapper.toDTO(updatedUser);


    }


    @Override
    public UserDto get(String id) {

        UserResource userResource = usersResource().get(id);

        UserRepresentation user = userResource.toRepresentation();

        List<String> realmRoles = userResource.roles().realmLevel().listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .filter(role -> !systemRoles.contains(role))
                .toList();

        user.setRealmRoles(realmRoles);

        return keycloakMapper.toDTO(user);
    }

    @Override
    public Page<UserDto> getAll(String keyword, String filter, int page, int size) {

        int first = page * size;

        List<UserRepresentation> users =
                usersResource().search(keyword, first, size);

        long total = usersResource().count(keyword);

        return new PageImpl<>(
                users.stream()
                        .map(keycloakMapper::toDTO)
                        .toList(),
                PageRequest.of(page, size),
                total
        );
    }

    @Override
    public void delete(String id) {

            usersResource()
                    .get(id)
                    .remove();
    }


    @Override
    public void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO) {
        // 1. Find user
        UserResource userResource = usersResource()
                .get(userRoleUpdateDTO.getIdUser());

        System.out.println("Role: "+ userRoleUpdateDTO.getRoleName());

        UserRepresentation user = userResource.toRepresentation();

        if (user == null) {
            throw IAMException.notFound("Utilisateur", "id", userRoleUpdateDTO.getIdUser());
        }

        // 2. Find new role
        RoleRepresentation newRole;

        try {
            newRole = rolesResource()
                    .get(userRoleUpdateDTO.getRoleName())
                    .toRepresentation();

        } catch (NotFoundException e) {
            throw IAMException.notFound("Rôle", "Nom", userRoleUpdateDTO.getRoleName());

        }

      //  RoleDTO roleDTO = roleRepositoryPort.getRoleByName(userRoleUpdateDTO.getRoleName());

        // 3. Get current realm roles
        List<RoleRepresentation> currentRoles = userResource
                .roles()
                .realmLevel()
                .listAll();

        List<RoleRepresentation> rolesToRemove = currentRoles.stream()
                .filter(role -> !systemRoles.contains(role.getName()))
                .toList();

        // 4. Remove current roles
        if (!rolesToRemove.isEmpty()) {
            userResource
                    .roles()
                    .realmLevel()
                    .remove(rolesToRemove);
        }

        // 5. Assign new role
        userResource
                .roles()
                .realmLevel()
                .add(List.of(newRole));

    }

    @Override
    public boolean existsByUsername(String username) {
        return !usersResource()
                .searchByUsername(username, true)
                .isEmpty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return !usersResource()
                .searchByEmail(email, true)
                .isEmpty();
    }

    @Override
    public boolean existsByUsernameAndIdNot(String username, String idUser) {
        return usersResource()
                .searchByUsername(username, true)
                .stream()
                .anyMatch(user -> !user.getId().equals(idUser));
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, String idUser) {
        return usersResource()
                .searchByEmail(email, true)
                .stream()
                .anyMatch(user -> !user.getId().equals(idUser));
    }

    @Override
    public boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, String idUser) {
        return usersResource()
                .search(null, firstName, lastName, null, 0, 100)
                .stream()
                .anyMatch(user ->
                        !user.getId().equals(idUser)
                                && firstName.equalsIgnoreCase(user.getFirstName())
                                && lastName.equalsIgnoreCase(user.getLastName())
                );
    }

    @Override
    public void resetPassword(String email, String password) {

        UserRepresentation user = usersResource().searchByEmail(email, true).stream().findFirst().get();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(password);

        usersResource()
                .get(user.getId())
                .resetPassword(credential);
    }

    @Override
    public void assignRoles(String userId, List<String> roles) {
        List<RoleRepresentation> roleRepresentations =
                roles.stream()
                        .map(role ->
                                realm()
                                        .roles()
                                        .get(role)
                                        .toRepresentation())
                        .toList();

        usersResource()
                .get(userId)
                .roles()
                .realmLevel()
                .add(roleRepresentations);
    }

    @Override
    public void removeRoles(String userId, List<String> roles) {

    }

    @Override
    public void enable(String userId) {

        UserResource user = usersResource().get(userId);

        UserRepresentation rep = user.toRepresentation();

        rep.setEnabled(true);

        user.update(rep);
    }

    @Override
    public void disable(String userId) {
        UserResource user = usersResource().get(userId);

        UserRepresentation rep = user.toRepresentation();

        rep.setEnabled(false);

        user.update(rep);
    }

    private String capitalize(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }

        return value.substring(0, 1).toUpperCase()
                + value.substring(1).toLowerCase();
    }

}
