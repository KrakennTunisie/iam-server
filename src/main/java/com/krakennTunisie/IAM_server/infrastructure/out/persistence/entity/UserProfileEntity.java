package com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity;

import com.krakennTunisie.IAM_server.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;

    @Column(nullable = false, unique = true)
    private String keycloakUserId;

    private String firstName;

    private String lastName;


    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
