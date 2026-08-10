package com.krakennTunisie.IAM_server.application.ports.in;

public interface ClientUseCase {
    void createIfAbsent(String clientId);
}
