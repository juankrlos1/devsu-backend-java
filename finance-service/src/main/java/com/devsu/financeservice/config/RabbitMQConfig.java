package com.devsu.financeservice.config;

import com.devsu.financeservice.infrastructure.messaging.ClientMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

@Configuration
@EnableRabbit
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

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(clientQueue());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(ClientMessageListener listener) {
        return new MessageListenerAdapter(listener, "handleMessage");
    }
}
