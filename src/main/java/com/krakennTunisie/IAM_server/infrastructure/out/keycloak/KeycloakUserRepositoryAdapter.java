package com.krakennTunisie.IAM_server.infrastructure.out.keycloak;

import com.krakennTunisie.IAM_server.application.ports.out.UserRepositoryPort;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.addUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.mapper.KeycloakMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KeycloakUserRepositoryAdapter  implements UserRepositoryPort {

    KeycloakMapper keycloakMapper;

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void addUser(addUserDTO user) {

        UserRepresentation representation = keycloakMapper.toRepresentation(user);
        UsersResource usersResource = keycloak.realm(realm).users();

        try(Response response = usersResource.create(representation)){

            int status = response.getStatus();
            if(status ==201)
            {
                String location = response.getHeaderString("Location");
                String keycloakId = location.substring(location.lastIndexOf('/')+1);


                /*usersResource.get(keycloakId)
                        .roles()
                        .realmLevel()
                        .add(user.getRole());*/

            }
        }

    }
}
