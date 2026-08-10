package com.krakennTunisie.IAM_server.infrastructure.out.persistence.repository;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.PasswordResetRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, UUID> {

    Optional<PasswordResetRequest> findByEmailAndExpiresAtAfterAndOtpVerifiedFalse(
            String email, Instant now
    );

    Optional<PasswordResetRequest> findByResetTokenAndExpiresAtAfter(
            String resetToken, Instant now
    );

    void deleteByEmail(String email);
}
