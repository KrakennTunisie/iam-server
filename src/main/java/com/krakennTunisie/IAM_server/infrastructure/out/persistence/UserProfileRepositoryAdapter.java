package com.krakennTunisie.IAM_server.infrastructure.out.persistence;


import com.krakennTunisie.IAM_server.application.annotations.Audit;
import com.krakennTunisie.IAM_server.application.ports.out.MailNotificationRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.RoleRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.UserProfileRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.UserRepositoryPort;
import com.krakennTunisie.IAM_server.domain.enums.AuditAction;
import com.krakennTunisie.IAM_server.domain.enums.UserStatus;
import com.krakennTunisie.IAM_server.domain.exception.IAMException;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserProfileDTO;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UpdateUserRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.UserDto;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.*;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.UserProfileEntity;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.mapper.UserProfileMapper;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.repository.UserProfileRepository;
import com.krakennTunisie.IAM_server.shared.MailJobRequestFactory;
import com.krakennTunisie.IAM_server.shared.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserProfileRepositoryAdapter implements UserProfileRepositoryPort {

    private final UserProfileRepository userProfileRepository;

    private final UserRepositoryPort userRepositoryPort;

    private final MailNotificationRepositoryPort mailNotificationRepositoryPort;

    private final MailJobRequestFactory mailJobRequestFactory;

    private final RoleRepositoryPort roleRepositoryPort;

    private final UserProfileMapper userProfileMapper;

    private final PasswordGenerator passwordGenerator;


    @Audit(action = AuditAction.CREATE_USER, entity = "USER")
    @Override
    @Transactional
    public UserResponseDTO create(AddUserDTO request) {

        if(userProfileRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw IAMException.alreadyExists("Utilisateur", "email", request.getEmail());
        }

        request.setPassword(passwordGenerator.generate(12));

        UserDto userDto = userRepositoryPort.create(request);

        MailJobRequest mailJobRequest = mailJobRequestFactory.createAccountCreated(request.getEmail(), request.getEmail(), request.getPassword());

        mailNotificationRepositoryPort.createMailJob(mailJobRequest);

        AddUserProfileDTO userProfileDTO  = AddUserProfileDTO.builder()
                .keycloakUserId(userDto.getId())
                .addUserDTO(request)
                .build();

        UserProfileEntity profile = userProfileMapper.toEntity(userProfileDTO);

        return userProfileMapper.toModel(userProfileRepository.save(profile), userDto);

    }

    @Override
    @Transactional
    public UserResponseDTO update(String s, UpdateUserRequest request) {

        UserProfileEntity entity = userProfileRepository.findByKeycloakUserId(s);

        entity.setFirstName(request.getFirstname());
        entity.setLastName(request.getLastname());
        entity.setEmail(request.getEmail());

        if(request.getPhoneNumber()!=null){
            entity.setPhoneNumber(request.getPhoneNumber());
        }

        UserProfileEntity savedEntity = userProfileRepository.save(entity);

        UserDto userDto = userRepositoryPort.update(s, request);

        return userProfileMapper.toModel(
                savedEntity,
                userDto
        );
    }

    @Override
    public UserResponseDTO get(String s) {

        if(!userProfileRepository.existsByKeycloakUserId(s)){
            throw IAMException.notFound("Utilisateur", "id Keycloak", s);
        }
        UserProfileEntity entity = userProfileRepository.findByKeycloakUserId(s);

        UserDto userDto =
                userRepositoryPort.get(
                        entity.getKeycloakUserId()
                );

        return userProfileMapper.toModel(
                entity,
                userDto
        );

    }

    @Override
    public Page<UserResponseDTO> getAll(String keyword, String filter, int page, int size) {
        /*Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<UserProfileEntity> profiles =
                userProfileRepository.findAllWithFilters(keyword, filter, pageable);

        List<UserResponseDTO> users = profiles
                .getContent()
                .stream()
                .map(profile -> {

                    UserDto userDto =
                            userRepositoryPort.get(
                                    profile.getKeycloakUserId()
                            );

                    return userProfileMapper.toModel(
                            profile,
                            userDto
                    );
                })
                .toList();

        return new PageImpl<>(
                users,
                pageable,
                profiles.getTotalElements()
        );*/
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public Page<UserResponseDTO> getAllUsers(String keyword, String statusFilter, String roleFilter, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<UserProfileEntity> profiles =
                userProfileRepository.findAllWithFilters(keyword,statusFilter, roleFilter, pageable);

        List<UserResponseDTO> users =
                profiles.getContent().isEmpty() ?
                    new ArrayList<>()
                :
                profiles
                .getContent()
                .stream()
                .map(profile -> {

                    UserDto userDto =
                            userRepositoryPort.get(
                                    profile.getKeycloakUserId()
                            );

                    return userProfileMapper.toModel(
                            profile,
                            userDto
                    );
                })
                .toList();

        return new PageImpl<>(
                users,
                pageable,
                profiles.getTotalElements()
        );
    }

    @Override
    public List<String> getAllUsersIDsByRole(String role) {
        return userProfileRepository.findUserIdsByRole(role).stream().map(
                String::valueOf
        ).toList();
    }

    @Override
    public UserDetailsDTO getUserDetails(String idUser) {
        if(!userProfileRepository.existsByKeycloakUserId(idUser)){
            throw IAMException.notFound("Utilisateur", "id Keycloak", idUser);
        }
        UserProfileEntity entity = userProfileRepository.findByKeycloakUserId(idUser);

        UserDto userDto =
                userRepositoryPort.get(
                        entity.getKeycloakUserId()
                );
        List<RoleDTO> roles = new ArrayList<>();
        for (String roleName: userDto.getRoles()){
            RoleDTO roleDTO =  roleRepositoryPort.getRoleByName(roleName);
            roles.add(roleDTO);
        }

        return userProfileMapper.toDetailsModel(
                entity,
                userDto,
                roles
        );
    }

    @Override
    public void updateUserRole(UserRoleUpdateDTO userRoleUpdateDTO) {

        if(!userProfileRepository.existsByKeycloakUserId(userRoleUpdateDTO.getIdUser())){
            throw IAMException.notFound("Utilisateur", "id Keycloack", userRoleUpdateDTO.getIdUser());
        }
        userRepositoryPort.updateUserRole(userRoleUpdateDTO);

        UserProfileEntity userProfileEntity = userProfileRepository.findByKeycloakUserId(userRoleUpdateDTO.getIdUser());

       // MailJobRequest mailJobRequest = mailJobRequestFactory.createRoleAssigned(userProfileEntity.getEmail(), userRoleUpdateDTO.getRoleName());

        //mailNotificationRepositoryPort.createMailJob(mailJobRequest);

        userProfileEntity.setRole(userRoleUpdateDTO.getRoleName());

        userProfileRepository.save(userProfileEntity);
    }

    @Override
    public boolean existsById(String idUser) {
        return userProfileRepository.existsByKeycloakUserId(idUser);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userProfileRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, String idUser) {
        return userProfileRepository.existsByEmailIgnoreCaseAndKeycloakUserIdNot(email, idUser);
    }

    @Override
    public boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, String idUser) {
        return
                userProfileRepository
                        .existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndKeycloakUserIdNot(firstName, lastName, idUser);
    }

    @Override
    public void enable(String userId) {
        try {
            if(!userProfileRepository.existsByKeycloakUserId(userId)){
                throw IAMException.notFound("Utilisateur", "id Keycloack", userId);
            }
            userRepositoryPort.enable(userId);

            UserProfileEntity entity = userProfileRepository.findByKeycloakUserId(userId);
            entity.setStatus(UserStatus.ACTIVE);

            MailJobRequest mailJobRequest = mailJobRequestFactory.createAccountActivation(entity.getEmail());

            mailNotificationRepositoryPort.createMailJob(mailJobRequest);

            userProfileRepository.save(entity);

        }catch (Exception ex){
            throw IAMException.badRequest(ex.getMessage());
        }
    }

    @Override
    public void disable(String userId) {
        try {
            if(!userProfileRepository.existsByKeycloakUserId(userId)){
                throw IAMException.notFound("Utilisateur", "id Keycloack", userId);
            }
            userRepositoryPort.disable(userId);

            UserProfileEntity entity = userProfileRepository.findByKeycloakUserId(userId);
            entity.setStatus(UserStatus.BLOCKED);

            MailJobRequest mailJobRequest = mailJobRequestFactory.createAccountDeactivation(entity.getEmail());

            mailNotificationRepositoryPort.createMailJob(mailJobRequest);

            userProfileRepository.save(entity);

        }catch (Exception ex){
            throw IAMException.badRequest(ex.getMessage());
        }
    }
}
