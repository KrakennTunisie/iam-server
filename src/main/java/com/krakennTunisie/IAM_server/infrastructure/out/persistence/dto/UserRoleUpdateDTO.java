package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRoleUpdateDTO {

    private String idUser;

    private String roleName;
}
