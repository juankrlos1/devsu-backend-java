package com.devsu.financeservice.infrastructure.messaging;

import com.devsu.financeservice.api.dto.ClientDto;
import com.devsu.financeservice.config.ClientCache;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ClientRequestService {

    private final RabbitTemplate rabbitTemplate;
    private final ClientCache clientCache;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    private final ConcurrentHashMap<Long, CompletableFuture<ClientDto>> pendingRequests = new ConcurrentHashMap<>();

    public ClientDto requestClient(Long clientId) {
        CompletableFuture<ClientDto> future = new CompletableFuture<>();
        pendingRequests.put(clientId, future);
        rabbitTemplate.convertAndSend(exchange, "clientRequestQueue", clientId);
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch client information", e);
        }
    }

    public void handleClientResponse(ClientDto clientDto) {
        CompletableFuture<ClientDto> future = pendingRequests.remove(clientDto.getClientId());
        if (future != null) {
            clientCache.put(clientDto.getClientId(), clientDto);
            future.complete(clientDto);
        }
    }
}