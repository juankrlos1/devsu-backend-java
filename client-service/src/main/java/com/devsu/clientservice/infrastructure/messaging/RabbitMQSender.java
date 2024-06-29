package com.devsu.clientservice.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public RabbitMQSender(RabbitTemplate rabbitTemplate,
                          @Value("${rabbitmq.exchange}") String exchange,
                          @Value("${rabbitmq.routingKey}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void send(Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
