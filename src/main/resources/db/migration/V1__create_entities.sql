CREATE TABLE IF NOT EXISTS audit_logs
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
    CONSTRAINT pk_audit_logs PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS user_profiles
(
    id_user          UUID NOT NULL,
    keycloak_user_id VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL,
    status           VARCHAR(255),
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_user_profiles PRIMARY KEY (id_user)
    );

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uc_user_profiles_email'
    ) THEN
ALTER TABLE user_profiles
    ADD CONSTRAINT uc_user_profiles_email
        UNIQUE (email);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uc_user_profiles_keycloakuserid'
    ) THEN
ALTER TABLE user_profiles
    ADD CONSTRAINT uc_user_profiles_keycloakuserid
        UNIQUE (keycloak_user_id);
END IF;
END $$;