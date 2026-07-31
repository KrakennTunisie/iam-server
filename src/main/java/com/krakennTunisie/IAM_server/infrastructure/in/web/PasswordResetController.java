package com.krakennTunisie.IAM_server.infrastructure.in.web;

import com.krakennTunisie.IAM_server.application.ports.in.ResetPasswordUseCase;
import com.krakennTunisie.IAM_server.infrastructure.in.dto.ForgotPasswordRequestDTO;
import com.krakennTunisie.IAM_server.infrastructure.in.dto.ResetPasswordRequestDTO;
import com.krakennTunisie.IAM_server.infrastructure.in.dto.VerifyOtpRequestDTO;
import com.krakennTunisie.IAM_server.infrastructure.in.dto.VerifyOtpResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class PasswordResetController {
    private final ResetPasswordUseCase passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<Void> requestOtp(@Valid @RequestBody ForgotPasswordRequestDTO dto) {
        passwordResetService.requestOTP(dto.email());
        // Toujours 200, que l'email existe ou non -> pas d'énumération de comptes
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyOtpResponseDTO> verifyOtp(@Valid @RequestBody VerifyOtpRequestDTO dto) {
        String resetToken = passwordResetService.verifyOtp(dto.email(), dto.otp());
        return ResponseEntity.ok(new VerifyOtpResponseDTO(resetToken));
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO dto) {
        passwordResetService.resetPassword(dto.resetToken(), dto.newPassword(), dto.confirmPassword());
        return ResponseEntity.ok().build();
    }
}
