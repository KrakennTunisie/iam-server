package com.krakennTunisie.IAM_server.application.ports.out;

public interface ClientRepositoryPort {

    void createIfAbsent(String clientId);
}
