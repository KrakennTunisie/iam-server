CREATE TABLE IF NOT EXISTS mail_notification_attachments
(
    id                       UUID         NOT NULL,
    id_document              VARCHAR(255),
    file_name                VARCHAR(255) NOT NULL,
    file_path                VARCHAR(255) NOT NULL,
    id_mail_job_notification UUID         NOT NULL,
    CONSTRAINT pk_mail_notification_attachments PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS mail_notification_jobs
(
    id_mail_job_notification UUID         NOT NULL,
    to_email                 VARCHAR(255) NOT NULL,
    subject                  VARCHAR(255) NOT NULL,
    body                     VARCHAR(5000),
    event_type               VARCHAR(255) NOT NULL,
    aggregate_id             VARCHAR(255),
    status                   VARCHAR(255) NOT NULL,
    retry_count              INTEGER      NOT NULL,
    max_retries              INTEGER      NOT NULL,
    last_error               VARCHAR(255),
    last_attempt_at          TIMESTAMP WITHOUT TIME ZONE,
    sent_at                  TIMESTAMP WITHOUT TIME ZONE,
    created_at               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id                 UUID         NOT NULL,
    CONSTRAINT pk_mail_notification_jobs PRIMARY KEY (id_mail_job_notification)
    );

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uc_mail_notification_jobs_eventid'
    ) THEN
ALTER TABLE mail_notification_jobs
    ADD CONSTRAINT uc_mail_notification_jobs_eventid
        UNIQUE (event_id);
END IF;
END
$$;


CREATE INDEX IF NOT EXISTS idx_notification_jobs_created_at
    ON mail_notification_jobs (created_at);

CREATE INDEX IF NOT EXISTS idx_notification_jobs_status
    ON mail_notification_jobs (status);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'fk_mail_notification_attachments_on_idmailjobnotification'
    ) THEN
ALTER TABLE mail_notification_attachments
    ADD CONSTRAINT fk_mail_notification_attachments_on_idmailjobnotification
        FOREIGN KEY (id_mail_job_notification)
            REFERENCES mail_notification_jobs (id_mail_job_notification);
END IF;
END
$$;