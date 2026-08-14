package com.krakennTunisie.IAM_server.shared;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "kerp.security")
public class SecurityProperties {

    private List<ClientProperties> clients;

    @Getter
    @Setter
    public static class ClientProperties {
        private String clientId;
        private List<PermissionProperties> permissions;
    }

    @Getter
    @Setter
    public static class PermissionProperties {
        private String name;
        private String description;
    }
}
