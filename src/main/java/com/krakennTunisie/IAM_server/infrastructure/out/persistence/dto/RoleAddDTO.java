package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class RoleAddDTO {

    private String name;
    private String description;

    @Setter
    private List<RolePermissionDTO> permissionDTOList;

}
