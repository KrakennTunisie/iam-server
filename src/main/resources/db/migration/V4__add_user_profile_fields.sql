ALTER TABLE user_profile_entity
    ADD COLUMN IF NOT EXISTS first_name VARCHAR(255);

ALTER TABLE user_profile_entity
    ADD COLUMN IF NOT EXISTS last_name VARCHAR(255);

ALTER TABLE user_profile_entity
    ADD COLUMN IF NOT EXISTS role VARCHAR(255);

CREATE UNIQUE INDEX IF NOT EXISTS uc_userprofileentity_email
    ON user_profile_entity (email);

CREATE UNIQUE INDEX IF NOT EXISTS uc_userprofileentity_keycloakuserid
    ON user_profile_entity (keycloak_user_id);