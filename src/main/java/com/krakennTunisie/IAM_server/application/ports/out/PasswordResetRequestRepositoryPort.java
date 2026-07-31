package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.PasswordResetRequest;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRequestRepositoryPort {

    void saveOTP(String email);

    String verifyOtp(String email, String otp);

    Optional<PasswordResetRequest> findByResetTokenAndExpiresAtAfter(
            String resetToken, Instant now
    );

    void delete(UUID uuid);
}
