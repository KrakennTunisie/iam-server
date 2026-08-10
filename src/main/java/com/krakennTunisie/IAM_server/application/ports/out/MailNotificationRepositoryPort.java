package com.krakennTunisie.IAM_server.application.ports.out;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.MailJobRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.MailNotificationJobEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MailNotificationRepositoryPort {
    List<MailNotificationJobEntity> findJobsToPublish(Pageable pageable);


    void saveAll(List<MailNotificationJobEntity> entities);


    void createMailJob(MailJobRequest mailJobRequest);
}
