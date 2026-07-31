package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserDto {

    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private boolean enabled;

    private List<String> roles;

}
