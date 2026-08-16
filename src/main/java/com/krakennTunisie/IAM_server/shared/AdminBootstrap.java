package com.krakennTunisie.IAM_server.shared;

import com.krakennTunisie.IAM_server.application.ports.in.UserProfileUseCase;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.config.AdminBootstrapProperties;
import com.krakennTunisie.IAM_server.infrastructure.out.keycloak.dto.AddUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminBootstrap {

    private final AdminBootstrapProperties properties;
    private final UserProfileUseCase userService;

    @EventListener(RoleInitializedEvent.class)
    public void initialize() {

        if (!properties.isEnabled()) {
            return;
        }

        if (userService.existsByEmail(properties.getEmail())) {
            log.info("Admin user already exists.");
            return;
        }

        AddUserDTO request = AddUserDTO.builder()
                .username(properties.getUsername())
                .email(properties.getEmail())
                .firstname(properties.getFirstname())
                .lastname(properties.getLastname())
                .phoneNumber(properties.getPhoneNumber())
                .password(properties.getPassword())
                .status(properties.getStatus())
                .role(properties.getRole())
                .build();


        userService.create(request);

        log.info("Default administrator created successfully.");
    }
}
