package com.devsu.financeservice.config;

import com.devsu.financeservice.api.dto.ClientDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientCache {

    private final ConcurrentHashMap<Long, ClientDto> cache = new ConcurrentHashMap<>();

    public ClientDto get(Long clientId) {
        return cache.get(clientId);
    }

    public void put(Long clientId, ClientDto clientDto) {
        cache.put(clientId, clientDto);
    }

    public void remove(Long clientId) {
        cache.remove(clientId);
    }
}