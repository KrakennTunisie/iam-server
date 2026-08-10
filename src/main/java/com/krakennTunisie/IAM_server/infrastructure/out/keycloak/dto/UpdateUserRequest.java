package com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateUserRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le prénom est obligatoire")
    private String lastname;

    @Email
    private String email;

    private String phoneNumber;

}
