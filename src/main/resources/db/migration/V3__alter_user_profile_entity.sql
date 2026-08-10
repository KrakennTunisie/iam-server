ALTER TABLE user_profile_entity
    ADD COLUMN IF NOT EXISTS phone_number VARCHAR(255);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uc_userprofileentity_email'
          AND conrelid = 'user_profile_entity'::regclass
    )
    AND NOT EXISTS (
        SELECT 1
        FROM pg_class
        WHERE relname = 'uc_userprofileentity_email'
    ) THEN

ALTER TABLE user_profile_entity
    ADD CONSTRAINT uc_userprofileentity_email
        UNIQUE (email);

END IF;
END $$;


DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uc_userprofileentity_keycloakuserid'
          AND conrelid = 'user_profile_entity'::regclass
    )
    AND NOT EXISTS (
        SELECT 1
        FROM pg_class
        WHERE relname = 'uc_userprofileentity_keycloakuserid'
    ) THEN

ALTER TABLE user_profile_entity
    ADD CONSTRAINT uc_userprofileentity_keycloakuserid
        UNIQUE (keycloak_user_id);

END IF;
END $$;