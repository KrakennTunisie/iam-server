package com.krakennTunisie.IAM_server.infrastructure.out.keycloak;

import com.krakennTunisie.IAM_server.application.ports.out.PasswordResetRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.PasswordResetRequestRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.UserRepositoryPort;
import com.krakennTunisie.IAM_server.domain.exception.IAMException;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.PasswordResetRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class PasswordResetRepositoryAdapter implements PasswordResetRepositoryPort {

    private final PasswordResetRequestRepositoryPort passwordResetRequestRepositoryPort;

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void requestOTP(String email) {
        passwordResetRequestRepositoryPort.saveOTP(email);
    }

    @Override
    public String verifyOtp(String email, String otp) {
        return passwordResetRequestRepositoryPort.verifyOtp(email,otp);
    }

    @Override
    public void resetPassword(String resetToken, String newPassword) {

        PasswordResetRequest request = passwordResetRequestRepositoryPort
                .findByResetTokenAndExpiresAtAfter(resetToken, Instant.now())
                .orElseThrow(()-> IAMException.badRequest("reset token expiré"));

        userRepositoryPort.resetPassword(request.getEmail(), newPassword);

        passwordResetRequestRepositoryPort.delete(request.getId()); // usage unique, supprimé après succès
    }

}
