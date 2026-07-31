package com.krakennTunisie.IAM_server.infrastructure.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krakennTunisie.IAM_server.application.ports.in.RoleUseCase;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleUseCase roleUseCase;


    @PostMapping
    public ResponseEntity<RoleDTO> create(
            @ModelAttribute RoleAddDTO request)throws JsonProcessingException {

        return ResponseEntity.ok(
                roleUseCase.create(request)
        );
    }

    @GetMapping
        public ResponseEntity<Page<RoleDTO>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            @RequestParam int page,
            @RequestParam(defaultValue = "3") int size ){

        return ResponseEntity.ok(roleUseCase.getAll(keyword, filter, page, size));


    }

    @GetMapping("/allRoles")
    public ResponseEntity<List<RoleSummaryDTO>> getAllRoles() {
        return ResponseEntity.ok(roleUseCase.getAllRoles());
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<RoleDTO> getRoleByName(
            @PathVariable String roleName
    ) {

        return ResponseEntity.ok(
                roleUseCase.getRoleByName(roleName)
        );
    }

    @PostMapping("/{roleName}/permissions")
    public ResponseEntity<RoleDTO> addPermissions(
            @PathVariable String roleName,
            @ModelAttribute AddPermissionsRequest addPermissionsRequest
            ) {

        return ResponseEntity.ok(
                roleUseCase.addPermissions(roleName, addPermissionsRequest.permissions())
        );
    }

    @DeleteMapping("/{roleName}/permissions/{clientId}/{permissionName}")
    public ResponseEntity<RoleDTO> revokePermission(
            @PathVariable String roleName,
            @PathVariable String clientId,
            @PathVariable String permissionName
    ) {
        RolePermissionDTO rolePermissionDTO = RolePermissionDTO.builder()
                .clientId(clientId)
                .name(permissionName)
                .description(null)
                .build();
        return ResponseEntity.ok(
                roleUseCase.revokePermission(
                        roleName,
                       rolePermissionDTO
                )
        );
    }
}
