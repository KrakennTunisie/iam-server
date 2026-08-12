package com.krakennTunisie.IAM_server.infrastructure.in.web;

import com.krakennTunisie.IAM_server.application.ports.in.UserProfileUseCase;
import com.krakennTunisie.IAM_server.application.ports.in.UserUseCase;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserDetailsDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserResponseDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    private final UserProfileUseCase userProfileUseCase;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @Valid @ModelAttribute AddUserDTO request) {

        return ResponseEntity.ok(
                userProfileUseCase.create(request)
        );
    }

    @PatchMapping("/{id}/role/{roleName}")
    public ResponseEntity<Void> updateUserRole(
            @PathVariable String id,
            @PathVariable String roleName
    ) {
        UserRoleUpdateDTO userRoleUpdateDTO = UserRoleUpdateDTO.builder()
                .idUser(id)
                .roleName(roleName)
                .build();
        userProfileUseCase.updateUserRole(userRoleUpdateDTO);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable String id,
            @Valid @ModelAttribute UpdateUserRequest request) {

        return ResponseEntity.ok(userUseCase.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(
            @PathVariable String id) {


        return ResponseEntity.ok(
                userProfileUseCase.get(id)
        );
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<String>> getUsersIdsByRoleName(
            @PathVariable String roleName) {


        return ResponseEntity.ok(
                userProfileUseCase.getAllUsersIDsByRole(roleName)
        );
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<UserDetailsDTO> getUserDetails(
            @PathVariable String id) {


        return ResponseEntity.ok(
                userProfileUseCase.getUserDetails(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String statusFilter,
            @RequestParam(required = false) String roleFilter,
            @RequestParam int page,
            @RequestParam(defaultValue = "5") int size ){

       return ResponseEntity.ok(userProfileUseCase.getAllUsers(keyword, statusFilter, roleFilter, page, size));


    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String id) {

        userUseCase.delete(id);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> resetPassword(
            @PathVariable String id,
            @Valid @RequestBody String request) {

        userUseCase.resetPassword(id, request);

        return ResponseEntity.ok(
                "Mot de passe changé avec succès"
        );
    }
/*

    @PutMapping("/{id}/roles")
    public ResponseEntity<Void> assignRoles(
            @PathVariable String id,
            @Valid @RequestBody String request) {

        userUseCase.assignRoles(id, request);

        return ResponseEntity.ok()(
                "Roles assigned successfully",
                null
        );
    }

    @DeleteMapping("/{id}/roles")
    public ResponseEntity<Void> removeRoles(
            @PathVariable String id,
            @Valid @RequestBody String request) {

        userUseCase.removeRoles(id, request);

        return ResponseEntity.ok()(
                "Roles removed successfully",
                null
        );
    }
*/

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(
            @PathVariable String id) {

        userProfileUseCase.enable(id);

        return ResponseEntity.ok(
                "User enabled successfully"
        );
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(
            @PathVariable String id) {

        userProfileUseCase.disable(id);

        return ResponseEntity.ok(
                "User disabled successfully"
        );
    }
}
