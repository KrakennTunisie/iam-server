package com.krakennTunisie.IAM_server.infrastructure.in.web;

import com.krakennTunisie.IAM_server.application.ports.in.PermissionUseCase;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.ClientPermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.PermissionDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.RoleDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.RolePermissionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionUseCase permissionUseCase;

    @GetMapping
    public ResponseEntity<Page<ClientPermissionDTO>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size ){

        return ResponseEntity.ok(permissionUseCase.getAll(keyword, filter, page, size));


    }

}
