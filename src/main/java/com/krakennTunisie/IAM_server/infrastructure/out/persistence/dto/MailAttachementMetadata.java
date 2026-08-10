package com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto;

import java.util.UUID;

public record MailAttachementMetadata(
        UUID id,

        String idDocument,

        String fileName,

        String filePath,

        String contentType
) {
}
