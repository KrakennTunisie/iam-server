package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper;

import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.addUserDTO;
import org.keycloak.representations.idm.UserRepresentation;

public class KeycloakMapper {


    //-----------------------------------------
    // Mapping : domaine -> keycloak representation
    //--------------------------------------------
    public UserRepresentation toRepresentation(addUserDTO user)
    {
        UserRepresentation rep = new UserRepresentation();
        rep.setUsername(user.getUsername());
        rep.setEmail(user.getEmail());
        rep.setLastName(user.getLastname());
        rep.setEnabled(user.getStatus());
        rep.setEmailVerified(true);

        return rep;

    }
}
