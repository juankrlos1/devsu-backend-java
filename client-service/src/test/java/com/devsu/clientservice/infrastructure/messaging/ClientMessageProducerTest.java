package com.devsu.clientservice.infrastructure.messaging;

import com.devsu.clientservice.api.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientMessageProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ClientMessageProducer clientMessageProducer;

    private ClientDto clientDto;

    @BeforeEach
    void setUp() {
        clientDto = new ClientDto();
        clientDto.setClientId(1L);
        clientDto.setName("Jose Lema");
        clientDto.setIdentification("1723456789");
    }

    @Test
    @DisplayName("Enviar actualización de cliente: Debería enviar un mensaje al intercambio y clave de enrutamiento correctos")
    void sendClientUpdate() {
        clientMessageProducer.sendClientUpdate(clientDto);

        verify(rabbitTemplate, times(1)).convertAndSend("client.exchange", "client.routingKey", clientDto);
    }
}
