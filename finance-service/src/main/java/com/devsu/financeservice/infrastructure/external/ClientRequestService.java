package com.devsu.financeservice.infrastructure.external;

import com.devsu.financeservice.api.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientRequestService {

    private final ClientServiceClient clientServiceClient;

    public ClientDto getClientById(Long clientId) {
        return clientServiceClient.getClientById(clientId).getData();
    }
}