package com.krakennTunisie.IAM_server.infrastructure.out.keycloak;

import com.krakennTunisie.IAM_server.application.ports.out.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakClientRepositoryAdapter implements ClientRepositoryPort {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    private RealmResource realm() {
        return keycloak.realm(realm);
    }

    @Override
    public void createIfAbsent(String clientId) {

        boolean exists = realm()
                .clients()
                .findByClientId(clientId)
                .stream()
                .findAny()
                .isPresent();

        if (exists) {
            return;
        }

        ClientRepresentation client = new ClientRepresentation();

        client.setClientId(clientId);

        client.setEnabled(true);

        client.setProtocol("openid-connect");

        client.setPublicClient(false);

        client.setServiceAccountsEnabled(true);

        client.setStandardFlowEnabled(false);

        client.setDirectAccessGrantsEnabled(false);

        client.setAuthorizationServicesEnabled(false);

        realm().clients().create(client);

    }
}
