package com.devsu.clientservice.infrastructure.mapper;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.UpdateClientDto;
import com.devsu.clientservice.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientMapperTest {

    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        clientMapper = Mappers.getMapper(ClientMapper.class);
    }

    @Test
    @DisplayName("Convertir Client a ClientDto: Debería mapear correctamente todos los campos")
    void toDto() {
        Client client = new Client();
        client.setClientId(1L);
        client.setPersonId(1L);
        client.setName("Jose Lema");
        client.setIdentification("1723456789");

        ClientDto clientDto = clientMapper.toDto(client);

        assertEquals(client.getClientId(), clientDto.getClientId());
        assertEquals(client.getPersonId(), clientDto.getPersonId());
        assertEquals(client.getName(), clientDto.getName());
        assertEquals(client.getIdentification(), clientDto.getIdentification());
    }

    @Test
    @DisplayName("Convertir ClientDto a Client: Debería mapear correctamente todos los campos")
    void toEntity() {
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(1L);
        clientDto.setPersonId(1L);
        clientDto.setName("Jose Lema");
        clientDto.setIdentification("1723456789");

        Client client = clientMapper.toEntity(clientDto);

        assertEquals(clientDto.getClientId(), client.getClientId());
        assertEquals(clientDto.getPersonId(), client.getPersonId());
        assertEquals(clientDto.getName(), client.getName());
        assertEquals(clientDto.getIdentification(), client.getIdentification());
    }

    @Test
    @DisplayName("Actualizar Client desde UpdateClientDto: Debería mapear correctamente todos los campos no ignorados")
    void updateEntityFromDto() {
        UpdateClientDto updateClientDto = new UpdateClientDto();
        updateClientDto.setName("Updated Name");
        updateClientDto.setPassword("new_password");
        updateClientDto.setStatus(true);
        updateClientDto.setGender("M");
        updateClientDto.setAge(30);
        updateClientDto.setIdentification("new_identification");
        updateClientDto.setAddress("new address");
        updateClientDto.setPhone("123456789");

        Client client = new Client();
        client.setClientId(1L);
        client.setPersonId(1L);
        client.setName("Jose Lema");
        client.setPassword("old_password");
        client.setStatus(false);
        client.setGender("M");
        client.setAge(35);
        client.setIdentification("1723456789");
        client.setAddress("old address");
        client.setPhone("0987654321");

        clientMapper.updateEntityFromDto(updateClientDto, client);

        assertEquals("Updated Name", client.getName());
        assertEquals("new_password", client.getPassword());
        assertTrue(client.isStatus());
        assertEquals("M", client.getGender());
        assertEquals(30, client.getAge());
        assertEquals("new_identification", client.getIdentification());
        assertEquals("new address", client.getAddress());
        assertEquals("123456789", client.getPhone());
        assertEquals(1L, client.getClientId()); // Verificar que no se actualizó
        assertEquals(1L, client.getPersonId()); // Verificar que no se actualizó
    }
}
