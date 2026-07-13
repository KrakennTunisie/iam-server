package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto;

import com.krakennTunisie.IAM_server.domain.model.Role;
import lombok.Getter;

import java.util.List;

@Getter
public class addUserDTO {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Boolean  status;
    private List<Role> role;

}
