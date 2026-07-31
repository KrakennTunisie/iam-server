package com.krakennTunisie.IAM_server.domain.model;

import java.util.List;

public class User{

    private String keycloakId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;

    private boolean enabled;
    private boolean emailVerified;

    private String temporaryPassword;


    private List<Role> roles;

}
