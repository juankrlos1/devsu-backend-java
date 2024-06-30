package com.devsu.clientservice.infrastructure.messaging;

import com.devsu.clientservice.api.dto.ClientDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendClientUpdate(ClientDto clientDto) {
        rabbitTemplate.convertAndSend("client.exchange", "client.routingKey", clientDto);
    }
}
