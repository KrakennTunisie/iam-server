package com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "password_reset_requests")
@Getter
@Setter
public class PasswordResetRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column( nullable = false)
    private String otpHash;

    @Column( unique = true)
    private String resetToken; // null tant que l'OTP n'est pas vérifié

    @Column( nullable = false)
    private boolean otpVerified = false;

    @Column( nullable = false)
    private int attempts = 0;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column( nullable = false)
    private Instant createdAt = Instant.now();

    // getters/setters
}
