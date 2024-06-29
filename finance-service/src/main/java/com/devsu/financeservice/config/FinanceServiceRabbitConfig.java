package com.devsu.financeservice.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinanceServiceRabbitConfig {

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("clientExchange");
    }

    @Bean
    public Queue clientRequestQueue() {
        return new Queue("clientRequestQueue");
    }

    @Bean
    public Queue clientResponseQueue() {
        return new Queue("clientResponseQueue");
    }
}
