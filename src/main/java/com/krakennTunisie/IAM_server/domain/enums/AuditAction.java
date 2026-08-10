package com.krakennTunisie.IAM_server.domain.enums;

public enum AuditAction {

    LOGIN,

    LOGOUT,

    CREATE_USER,
    UPDATE_USER,
    DELETE_USER,

    ENABLE_USER,
    DISABLE_USER,
    BLOCK_USER,

    RESET_PASSWORD,

    ASSIGN_ROLE,
    REMOVE_ROLE,

    CREATE_PARTNER,
    UPDATE_PARTNER,
    DELETE_PARTNER,

    CREATE_INVOICE,
    UPDATE_INVOICE,
    DELETE_INVOICE
}
