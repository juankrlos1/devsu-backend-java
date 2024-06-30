package com.devsu.clientservice.api.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientDtoTest {

    @Test
    @DisplayName("ClientDto: Debería crear un ClientDto con todos los campos")
    void createClientDto_AllFields() {
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(1L);
        clientDto.setName("Jose Lema");
        clientDto.setIdentification("1723456789");

        assertEquals(1L, clientDto.getClientId());
        assertEquals("Jose Lema", clientDto.getName());
        assertEquals("1723456789", clientDto.getIdentification());
    }

    @Test
    @DisplayName("ClientDto: Debería tener campos nulos por defecto")
    void createClientDto_DefaultFields() {
        ClientDto clientDto = new ClientDto();

        assertNull(clientDto.getClientId());
        assertNull(clientDto.getName());
        assertNull(clientDto.getIdentification());
    }
}

class UpdateClientDtoTest {

    @Test
    @DisplayName("UpdateClientDto: Debería crear un UpdateClientDto con todos los campos")
    void createUpdateClientDto_AllFields() {
        UpdateClientDto updateClientDto = new UpdateClientDto();
        updateClientDto.setName("Updated Name");
        updateClientDto.setPassword("new_password");
        updateClientDto.setStatus(true);
        updateClientDto.setGender("M");
        updateClientDto.setAge(30);
        updateClientDto.setIdentification("new_identification");
        updateClientDto.setAddress("new address");
        updateClientDto.setPhone("123456789");

        assertEquals("Updated Name", updateClientDto.getName());
        assertEquals("new_password", updateClientDto.getPassword());
        assertEquals(true, updateClientDto.getStatus());
        assertEquals("M", updateClientDto.getGender());
        assertEquals(30, updateClientDto.getAge());
        assertEquals("new_identification", updateClientDto.getIdentification());
        assertEquals("new address", updateClientDto.getAddress());
        assertEquals("123456789", updateClientDto.getPhone());
    }

    @Test
    @DisplayName("UpdateClientDto: Debería tener campos nulos por defecto")
    void createUpdateClientDto_DefaultFields() {
        UpdateClientDto updateClientDto = new UpdateClientDto();

        assertNull(updateClientDto.getName());
        assertNull(updateClientDto.getPassword());
        assertNull(updateClientDto.getStatus());
        assertNull(updateClientDto.getGender());
        assertNull(updateClientDto.getAge());
        assertNull(updateClientDto.getIdentification());
        assertNull(updateClientDto.getAddress());
        assertNull(updateClientDto.getPhone());
    }
}
