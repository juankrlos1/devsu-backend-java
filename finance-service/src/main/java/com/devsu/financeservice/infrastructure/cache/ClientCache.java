package com.devsu.financeservice.infrastructure.cache;

import com.devsu.financeservice.api.dto.ClientDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientCache {

    private final Map<Long, ClientDto> clientCache = new ConcurrentHashMap<>();

    public ClientDto getClientById(Long clientId) {
        return clientCache.get(clientId);
    }

    public void updateClient(ClientDto clientDto) {
        clientCache.put(clientDto.getClientId(), clientDto);
    }

    public void removeClient(Long clientId) {
        clientCache.remove(clientId);
    }

    public boolean containsClient(Long clientId) {
        return clientCache.containsKey(clientId);
    }
}
