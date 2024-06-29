package com.devsu.clientservice.infrastructure.messaging;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.domain.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEventHandler {

    private final ClientService clientService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    @RabbitListener(queues = "clientRequestQueue")
    public void handleClientRequest(Long clientId) {
        ClientDto clientDto = clientService.getClientById(clientId);
        rabbitTemplate.convertAndSend(exchange, "clientResponseQueue", clientDto);
    }
}
