package com.krakennTunisie.IAM_server.application.service;

import com.krakennTunisie.IAM_server.application.ports.in.ClientUseCase;
import com.krakennTunisie.IAM_server.application.ports.out.ClientRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService implements ClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    @Override
    public void createIfAbsent(String clientId) {
        clientRepositoryPort.createIfAbsent(clientId);
    }
}
