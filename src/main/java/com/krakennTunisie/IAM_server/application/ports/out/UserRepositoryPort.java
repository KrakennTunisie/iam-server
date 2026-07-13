package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.addUserDTO;

public interface UserRepositoryPort {

    void addUser(addUserDTO user);

}
