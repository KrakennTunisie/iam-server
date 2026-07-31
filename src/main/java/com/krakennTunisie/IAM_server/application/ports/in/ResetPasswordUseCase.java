package com.krakennTunisie.IAM_server.application.ports.in;

public interface ResetPasswordUseCase {

    void requestOTP(String email);

    String verifyOtp(String email, String otp);

    void resetPassword(String resetToken, String newPassword, String confirmPassword);
}
