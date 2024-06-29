package com.devsu.clientservice.infrastructure.messaging;

import com.devsu.clientservice.api.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public void publishClientCreated(ClientDto clientDto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, clientDto);
    }

    public void publishClientUpdated(ClientDto clientDto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, clientDto);
    }
}
