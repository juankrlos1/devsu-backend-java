package com.devsu.clientservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue clientQueue() {
        return new Queue("client.queue", false);
    }

    @Bean
    public Exchange clientExchange() {
        return new DirectExchange("client.exchange");
    }

    @Bean
    public Binding clientBinding(Queue clientQueue, Exchange clientExchange) {
        return BindingBuilder.bind(clientQueue).to(clientExchange).with("client.routingKey").noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
