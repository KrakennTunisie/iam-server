package com.krakennTunisie.IAM_server.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BillingPermission {

    /* ==========================================================
     * INVOICES
     * ========================================================== */
    BILLING_READ("View invoices"),
    BILLING_CREATE("Create invoices"),
    BILLING_UPDATE("Update invoices"),
    BILLING_DELETE("Delete invoices"),
    BILLING_SEND("Send invoices"),
    BILLING_CANCEL("Cancel invoices"),
    BILLING_EXPORT("Export invoices"),

    /* ==========================================================
     * PURCHASE ORDERS
     * ========================================================== */
    PURCHASE_ORDER_READ("View purchase orders"),
    PURCHASE_ORDER_CREATE("Create purchase orders"),
    PURCHASE_ORDER_UPDATE("Update purchase orders"),
    PURCHASE_ORDER_DELETE("Delete purchase orders"),
    PURCHASE_ORDER_APPROVE("Approve purchase orders"),
    PURCHASE_ORDER_CANCEL("Cancel purchase orders"),
    PURCHASE_ORDER_EXPORT("Export purchase orders"),

    /* ==========================================================
     * CUSTOMER CREDIT NOTES
     * ========================================================== */
    CUSTOMER_CREDIT_NOTE_READ("View customer credit notes"),
    CUSTOMER_CREDIT_NOTE_CREATE("Create customer credit notes"),
    CUSTOMER_CREDIT_NOTE_UPDATE("Update customer credit notes"),
    CUSTOMER_CREDIT_NOTE_DELETE("Delete customer credit notes"),
    CUSTOMER_CREDIT_NOTE_VALIDATE("Validate customer credit notes"),
    CUSTOMER_CREDIT_NOTE_EXPORT("Export customer credit notes"),

    /* ==========================================================
     * SUPPLIER CREDIT NOTES
     * ========================================================== */
    SUPPLIER_CREDIT_NOTE_READ("View supplier credit notes"),
    SUPPLIER_CREDIT_NOTE_CREATE("Create supplier credit notes"),
    SUPPLIER_CREDIT_NOTE_UPDATE("Update supplier credit notes"),
    SUPPLIER_CREDIT_NOTE_DELETE("Delete supplier credit notes"),
    SUPPLIER_CREDIT_NOTE_VALIDATE("Validate supplier credit notes"),
    SUPPLIER_CREDIT_NOTE_EXPORT("Export supplier credit notes"),

    /* ==========================================================
     * PAYMENTS
     * ========================================================== */
    PAYMENT_READ("View payments"),
    PAYMENT_CREATE("Register payments"),
    PAYMENT_UPDATE("Update payments"),
    PAYMENT_DELETE("Delete payments"),
    PAYMENT_VALIDATE("Validate payments"),
    PAYMENT_REFUND("Refund payments"),
    PAYMENT_EXPORT("Export payments"),

    /* ==========================================================
     * CLIENTS
     * ========================================================== */
    CLIENT_READ("View clients"),
    CLIENT_CREATE("Create clients"),
    CLIENT_UPDATE("Update clients"),
    CLIENT_DELETE("Delete clients"),

    /* ==========================================================
     * SUPPLIERS
     * ========================================================== */
    SUPPLIER_READ("View suppliers"),
    SUPPLIER_CREATE("Create suppliers"),
    SUPPLIER_UPDATE("Update suppliers"),
    SUPPLIER_DELETE("Delete suppliers"),

    /* ==========================================================
     * SETTINGS
     * ========================================================== */
    SETTINGS_READ("View billing settings"),
    SETTINGS_UPDATE("Update billing settings"),

    OPERATION_CATEGORY_READ("View operation categories"),
    OPERATION_CATEGORY_CREATE("Create operation categories"),
    OPERATION_CATEGORY_UPDATE("Update operation categories"),
    OPERATION_CATEGORY_DELETE("Delete operation categories"),

    PAYMENT_CONDITION_READ("View payment conditions"),
    PAYMENT_CONDITION_CREATE("Create payment conditions"),
    PAYMENT_CONDITION_UPDATE("Update payment conditions"),
    PAYMENT_CONDITION_DELETE("Delete payment conditions"),

    TVA_RATE_READ("View VAT rates"),
    TVA_RATE_CREATE("Create VAT rates"),
    TVA_RATE_UPDATE("Update VAT rates"),
    TVA_RATE_DELETE("Delete VAT rates");

    private final String description;

}
