package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper;

import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionDTO;
import com.krakennTunisie.IAM_server.shared.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class KeycloakMapper {

    //-----------------------------------------
    // Mapping : domaine -> keycloak representation
    //--------------------------------------------
    public UserRepresentation toRepresentation(AddUserDTO user)
    {
        UserRepresentation rep = new UserRepresentation();
        rep.setUsername(user.getUsername());
        rep.setFirstName(user.getFirstname());
        rep.setEmail(user.getEmail());
        rep.setLastName(user.getLastname());
        rep.setEnabled(true);
        rep.setEmailVerified(true);
        rep.setRequiredActions(Collections.emptyList()); // force aucune required action à la création

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(user.getPassword());
        rep.setCredentials(List.of(credential));


        return rep;

    }

    public UserDto toDTO(UserRepresentation userRepresentation){
        if(userRepresentation==null){
            return null;
        }
        System.out.println("userRepresentation: "+ userRepresentation.getRealmRoles());
        return UserDto.builder()
                .id(userRepresentation.getId())
                .username(userRepresentation.getUsername())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .email(userRepresentation.getEmail())
                .enabled(userRepresentation.isEnabled())
                .roles(userRepresentation.getRealmRoles())
                .build();
    }

    public PermissionDTO permissionToDTO(RoleRepresentation roleRepresentation){
        if(roleRepresentation==null){
            return null;
        }
        return PermissionDTO.builder()
                .id(roleRepresentation.getId())
                .name(roleRepresentation.getName())
                .description(roleRepresentation.getDescription())
                .build();
    }

    //public userProfile

}
