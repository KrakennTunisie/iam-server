package com.krakennTunisie.IAM_server.infrastructure.out.messaging;

import com.krakennTunisie.IAM_server.application.ports.out.AuditEventPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventPublisher implements AuditEventPublisherPort {

    private final KafkaTemplate<String, AuditEvent> kafkaTemplate;

    @Value("${kafka.topic.audit.event:kerp.audit.event}")
    private String topic;

    @Override
    public void publish(AuditEvent auditEvent) {
        kafkaTemplate.send(topic, String.valueOf(auditEvent.eventId()), auditEvent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error(
                                "Failed to send audit event {} to topic {}",
                                auditEvent.eventId(),
                                topic,
                                ex
                        );
                        return;
                    }

                    var metadata = result.getRecordMetadata();

                    log.info(
                            "Audit event sent successfully: topic={}, partition={}, offset={}",
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset()
                    );
                });
    }
}
