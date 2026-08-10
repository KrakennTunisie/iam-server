CREATE TABLE IF NOT EXISTS audit_log_entity
(
    id               UUID NOT NULL,
    keycloak_user_id VARCHAR(255),
    username         VARCHAR(255),
    action           VARCHAR(255),
    resource         VARCHAR(255),
    resource_id      VARCHAR(255),
    details          VARCHAR(2000),
    ip_address       VARCHAR(255),
    user_agent       VARCHAR(255),
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_auditlogentity PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS user_profile_entity
(
    id_user          UUID         NOT NULL,
    keycloak_user_id VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL,
    status           VARCHAR(255),
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_userprofileentity PRIMARY KEY (id_user)
    );

-- Unique constraints
CREATE UNIQUE INDEX IF NOT EXISTS uc_userprofileentity_email
    ON user_profile_entity (email);

CREATE UNIQUE INDEX IF NOT EXISTS uc_userprofileentity_keycloakuserid
    ON user_profile_entity (keycloak_user_id);

-- Drop old tables only if they exist
DROP TABLE IF EXISTS audit_logs CASCADE;

DROP TABLE IF EXISTS user_profiles CASCADE;