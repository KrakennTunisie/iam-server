package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddUserDTO {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String password;
    private String  status;
    private String role;

    @Override
    public String toString() {
        return "AddUserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}
