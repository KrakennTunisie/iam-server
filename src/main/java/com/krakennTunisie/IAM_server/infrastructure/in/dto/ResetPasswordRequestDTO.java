package com.krakennTunisie.IAM_server.infrastructure.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequestDTO(
        @NotBlank String resetToken,
        @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String newPassword,
        @NotBlank String confirmPassword
) {
}
