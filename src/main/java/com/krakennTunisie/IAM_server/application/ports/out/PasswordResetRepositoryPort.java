package com.krakennTunisie.IAM_server.application.ports.out;

public interface PasswordResetRepositoryPort {
    void requestOTP(String email);

    String verifyOtp(String email, String otp);

    void resetPassword(String resetToken, String newPassword);
}
