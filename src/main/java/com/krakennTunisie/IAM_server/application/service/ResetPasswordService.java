package com.krakennTunisie.IAM_server.application.service;

import com.krakennTunisie.IAM_server.application.ports.in.ResetPasswordUseCase;
import com.krakennTunisie.IAM_server.application.ports.out.PasswordResetRepositoryPort;
import com.krakennTunisie.IAM_server.application.ports.out.UserRepositoryPort;
import com.krakennTunisie.IAM_server.domain.exception.IAMException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResetPasswordService implements ResetPasswordUseCase {

    private final PasswordResetRepositoryPort passwordResetRepositoryPort;

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void requestOTP(String email) {
        if(!userRepositoryPort.existsByEmail(email)){
            throw IAMException.notFound("Utilisateur","email",email);
        }
        passwordResetRepositoryPort.requestOTP(email);
    }

    @Override
    public String verifyOtp(String email, String otp) {
        if(!userRepositoryPort.existsByEmail(email)){
            throw IAMException.notFound("Utilisateur","email",email);
        }
        return passwordResetRepositoryPort.verifyOtp(email, otp);
    }

    @Override
    public void resetPassword(String resetToken, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw IAMException.badRequest("Les mots de passe ne correspondent pas.");
        }
        passwordResetRepositoryPort.resetPassword(resetToken, newPassword);
    }
}
