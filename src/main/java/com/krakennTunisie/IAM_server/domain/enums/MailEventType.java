package com.krakennTunisie.IAM_server.domain.enums;

public enum MailEventType {
    // ===== Authentification / Utilisateurs (Keycloak) =====
    PASSWORD_RESET_OTP,           // OTP du stepper de reset password
    PASSWORD_CHANGED_CONFIRMATION,
    ACCOUNT_CREATED,
    ACCOUNT_ACTIVATION,
    ACCOUNT_DEACTIVATION,
    ROLE_ASSIGNED,
}
