package com.devsu.financeservice.infrastructure.messaging;

import com.devsu.financeservice.api.dto.ClientDto;
import com.devsu.financeservice.infrastructure.cache.ClientCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientMessageListener {

    private final ClientCache clientCache;

    public void handleMessage(ClientDto clientDto) {
        log.info("Received client update: {}", clientDto);
        clientCache.updateClient(clientDto);
    }
}
