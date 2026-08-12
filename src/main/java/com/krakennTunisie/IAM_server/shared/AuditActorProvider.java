package com.krakennTunisie.IAM_server.shared;

/*import com.krakennTunisie.IAM_server.infrastructure.out.messaging.AuditActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;*/

import java.util.Collections;
import java.util.List;
import java.util.Map;
/*
@Component
@Slf4j
public class AuditActorProvider {

    public AuditActor getCurrentActor() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        if (authentication == null ||
                !authentication.isAuthenticated()) {

            return null;
        }


        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Jwt jwt)) {
            log.warn(
                    "Current principal is not a JWT: {}",
                    principal.getClass().getName()
            );

            return null;
        }


        return new AuditActor(
                jwt.getSubject(),
                jwt.getClaimAsString("given_name"),
                jwt.getClaimAsString("family_name"),
                extractRoles(jwt)
        );
    }


    @SuppressWarnings("unchecked")
    private List<String> extractRoles(Jwt jwt) {

        Map<String, Object> realmAccess =
                jwt.getClaim("realm_access");


        if (realmAccess == null) {
            return Collections.emptyList();
        }


        Object roles =
                realmAccess.get("roles");


        if (roles instanceof List<?> roleList) {

            return roleList.stream()
                    .map(Object::toString)
                    .toList();
        }


        return Collections.emptyList();
    }
}*/
