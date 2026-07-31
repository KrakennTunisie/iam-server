package com.krakennTunisie.IAM_server.application.service;

import com.krakennTunisie.IAM_server.application.annotations.Audit;
import com.krakennTunisie.IAM_server.application.ports.in.UserUseCase;
import com.krakennTunisie.IAM_server.application.ports.out.UserRepositoryPort;
import com.krakennTunisie.IAM_server.domain.enums.AuditAction;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO) {
        userRepositoryPort.updateUserRole(userRoleUpdateDTO);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepositoryPort.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepositoryPort.existsByEmail(email);
    }

    @Override
    public void resetPassword(String userId, String password) {
        userRepositoryPort.resetPassword(userId, password);
    }

    @Override
    public void assignRoles(String userId, List<String> roles) {
        userRepositoryPort.assignRoles(userId, roles);
    }

    @Override
    public void removeRoles(String userId, List<String> roles) {
        userRepositoryPort.removeRoles(userId, roles);
    }

    @Override
    public void enable(String userId) {
        userRepositoryPort.enable(userId);
    }

    @Override
    public void disable(String userId) {
        userRepositoryPort.disable(userId);
    }


    @Audit(
            action = AuditAction.CREATE_USER,
            entity = "User"
    )
    @Override
    public UserDto create(AddUserDTO request) {
        System.out.println("User creation: "+request);
        return userRepositoryPort.create(request);
    }

    @Override
    public UserDto update(String s, UpdateUserRequest request) {
        return userRepositoryPort.update(s, request);
    }

    @Override
    public UserDto get(String s) {
        return userRepositoryPort.get(s);
    }

    @Override
    public Page<UserDto> getAll(String keyword, String filter, int page, int size) {
        return userRepositoryPort.getAll(keyword, filter, page, size);
    }

    @Override
    public void delete(String s) {
        userRepositoryPort.disable(s);
    }
}
