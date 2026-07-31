CREATE TABLE IF NOT EXISTS password_reset_requests
(
    id           UUID                        NOT NULL,
    email        VARCHAR(255)                NOT NULL,
    otp_hash     VARCHAR(255)                NOT NULL,
    reset_token  VARCHAR(255),
    otp_verified BOOLEAN                     NOT NULL,
    attempts     INTEGER                     NOT NULL,
    expires_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_password_reset_requests PRIMARY KEY (id)
    );

CREATE UNIQUE INDEX IF NOT EXISTS uc_password_reset_requests_resettoken
    ON password_reset_requests (reset_token);