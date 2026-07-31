package com.krakennTunisie.IAM_server.application.service;

import com.krakennTunisie.IAM_server.application.ports.in.UserProfileUseCase;
import com.krakennTunisie.IAM_server.application.ports.out.UserProfileRepositoryPort;
import com.krakennTunisie.IAM_server.domain.exception.IAMException;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserDetailsDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserResponseDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.UserRoleUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UseProfileService implements UserProfileUseCase {

    private final UserProfileRepositoryPort userProfileRepositoryPort;
    @Override
    public UserResponseDTO create(AddUserDTO request) {
        return userProfileRepositoryPort.create(request);
    }

    @Override
    public UserResponseDTO update(String s, UpdateUserRequest request) {
        if(!userProfileRepositoryPort.existsById(s)){
            throw IAMException.notFound("Utilisateur", "idKeycloack", s);
        }

        if(userProfileRepositoryPort.existsByEmailAndIdNot(request.getEmail(), s)){
            throw IAMException.alreadyExists("Utilisateur", "email", request.getEmail());
        }

        if(userProfileRepositoryPort.existsByFirstNameAndLastNameAndIdNot(request.getFirstname(), request.getLastname(), s)){
            throw IAMException.alreadyExists("Utilisateur", "Nom et prénom", request.getFirstname()+" "+request.getLastname());
        }
        return userProfileRepositoryPort.update(s, request);
    }

    @Override
    public UserResponseDTO get(String s) {
        return userProfileRepositoryPort.get(s);
    }

    @Override
    public Page<UserResponseDTO> getAll(String keyword, String filter, int page, int size) {
        return userProfileRepositoryPort.getAll(keyword, filter, page, size);
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public Page<UserResponseDTO> getAllUsers(String keyword, String statusFilter, String roleFilter, int page, int size) {
        return userProfileRepositoryPort.getAllUsers(keyword, statusFilter, roleFilter, page, size);
    }

    @Override
    public UserDetailsDTO getUserDetails(String userId) {
        return userProfileRepositoryPort.getUserDetails(userId);
    }

    @Override
    public void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO) {
        userProfileRepositoryPort.updateUserRole(userRoleUpdateDTO);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, String idUser) {
        return userProfileRepositoryPort.existsByEmailAndIdNot(email, idUser);
    }

    @Override
    public void enable(String userId) {
        userProfileRepositoryPort.enable(userId);
    }

    @Override
    public void disable(String userId) {
        userProfileRepositoryPort.disable(userId);
    }
}
