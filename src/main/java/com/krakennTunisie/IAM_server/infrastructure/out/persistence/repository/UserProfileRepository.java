package com.krakennTunisie.IAM_server.infrastructure.out.persistence.repository;

import com.krakennTunisie.IAM_server.infrastructure.out.persistence.entity.UserProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, UUID> {
    @Query("""
        SELECT u
        FROM UserProfileEntity u
        WHERE
            (
                :keyword IS NULL
                OR :keyword = ''
                OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
        AND
            (
                :statusFilter IS NULL
                OR :statusFilter = ''
                OR LOWER(u.status) = LOWER(:statusFilter)
            )
        AND
            (
                :roleFilter IS NULL
                OR :roleFilter = ''
                OR LOWER(u.role) = LOWER(:roleFilter)
            )
        """)
    Page<UserProfileEntity> findAllWithFilters(
            @Param("keyword") String keyword,
            @Param("statusFilter") String statusFilter,
            @Param("roleFilter") String roleFilter,
            Pageable pageable
    );

    boolean existsByKeycloakUserId(String keycloakUserId);

    UserProfileEntity findByKeycloakUserId(String keycloakUserId);

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailAndKeycloakUserIdNot(String email, String keycloakUserId);

    boolean existsByEmailIgnoreCaseAndKeycloakUserIdNot(String email, String keycloakUserId);

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndKeycloakUserIdNot(String firstName, String lastName, String keycloakUserId);
}
