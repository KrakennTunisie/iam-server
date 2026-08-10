package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import java.util.UUID;

public record MailJobAttachmentRequest(
        UUID attachmentRequestId,
        String fileName,
        String filePath
) {
}
