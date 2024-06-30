package com.devsu.financeservice.infrastructure.messaging;

import com.devsu.financeservice.api.dto.ClientDto;
import com.devsu.financeservice.infrastructure.cache.ClientCache;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientMessageConsumer {

    private final ClientCache clientCache;


    @RabbitListener(queues = "client.queue")
    public void receiveClientUpdate(ClientDto clientDto) {
        clientCache.updateClient(clientDto);
    }
}
