package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import com.krakennTunisie.IAM_server.domain.enums.MailEventType;

import java.util.List;

public record MailJobRequest(
        String toEmail,
        String subject,
        String body,
        MailEventType eventType,
        List<MailJobAttachmentRequest> attachments

) {
}
