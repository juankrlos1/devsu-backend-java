package com.devsu.financeservice.infrastructure.messaging;

import com.devsu.financeservice.api.dto.ClientDto;
import com.devsu.financeservice.config.ClientCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientEventListener {

    private final ClientCache clientCache;
    private final ClientRequestService clientRequestService;

    @RabbitListener(queues = "clientQueue")
    public void handleClientCreatedOrUpdate(ClientDto clientDto) {
        log.info("Received client created or updated event: {}", clientDto);
        clientCache.put(clientDto.getClientId(), clientDto);
    }

    @RabbitListener(queues = "clientResponseQueue")
    public void handleClientResponse(ClientDto clientDto) {
        log.info("Received client response: {}", clientDto);
        clientRequestService.handleClientResponse(clientDto);
    }
}