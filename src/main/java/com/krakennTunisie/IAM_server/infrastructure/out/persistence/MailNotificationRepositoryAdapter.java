package com.krakennTunisie.IAM_server.infrastructure.out.persistence;

import com.krakennTunisie.IAM_server.application.ports.out.MailNotificationRepositoryPort;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.dto.MailJobRequest;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.MailNotificationJobEntity;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.mapper.MailJobRequestMapper;
import com.krakennTunisie.IAM_server.infrastructure.out.persistence.repository.MailNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
@RequiredArgsConstructor
public class MailNotificationRepositoryAdapter implements MailNotificationRepositoryPort {

    private final MailNotificationRepository mailNotificationRepository;
    private final MailJobRequestMapper mailJobRequestMapper;


    @Override
    public List<MailNotificationJobEntity> findJobsToPublish(Pageable pageable) {
        return mailNotificationRepository.findJobsToPublish(pageable);
    }


    @Override
    public void saveAll(List<MailNotificationJobEntity> entities) {
        mailNotificationRepository.saveAll(entities);
    }

    @Override
    public void createMailJob(MailJobRequest mailJobRequest) {
        MailNotificationJobEntity mailNotificationJob = mailJobRequestMapper.toNotificationEntity(mailJobRequest);

        mailNotificationRepository.save(mailNotificationJob);
    }


}
