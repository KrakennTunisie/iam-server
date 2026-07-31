package com.krakennTunisie.IAM_server.shared;

import jakarta.ws.rs.core.Response;

public class ExtractErrorResponse {
    public static String extractKeycloakError(Response response) {
        try {
            if (response.hasEntity()) {
                String errorBody = response.readEntity(String.class);

                if (errorBody != null && !errorBody.isBlank()) {
                    return errorBody;
                }
            }

            return "Unknown error from Keycloak";

        } catch (Exception e) {
            return "Unable to read Keycloak error response";
        }
    }
}
