package com.devsu.clientservice.api.controller;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.UpdateClientDto;
import com.devsu.clientservice.common.dto.ApiResponse;
import com.devsu.clientservice.domain.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ClientDto clientDto1;
    private ClientDto clientDto2;

    @BeforeEach
    void setUp() {
        clientDto1 = new ClientDto();
        clientDto1.setClientId(1L);
        clientDto1.setName("Jose Lema");
        clientDto1.setIdentification("1723456789");

        clientDto2 = new ClientDto();
        clientDto2.setClientId(2L);
        clientDto2.setName("Marianela Montalvo");
        clientDto2.setIdentification("1346789012");
    }

    @Test
    @DisplayName("Obtener todos los clientes: Debería devolver una lista de todos los clientes con estado HTTP 200")
    void getAllClients() {
        List<ClientDto> clients = Arrays.asList(clientDto1, clientDto2);
        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<ApiResponse<List<ClientDto>>> response = clientController.getAllClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getData().size());
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    @DisplayName("Obtener cliente por ID: Debería devolver el cliente correspondiente con estado HTTP 200")
    void getClientById() {
        when(clientService.getClientById(1L)).thenReturn(clientDto1);

        ResponseEntity<ApiResponse<ClientDto>> response = clientController.getClientById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto1, Objects.requireNonNull(response.getBody()).getData());
        verify(clientService, times(1)).getClientById(1L);
    }

    @Test
    @DisplayName("Obtener cliente por identificación: Debería devolver el cliente correspondiente con estado HTTP 200")
    void getClientByIdentification() {
        when(clientService.getClientByIdentification("1723456789")).thenReturn(clientDto1);

        ResponseEntity<ApiResponse<ClientDto>> response = clientController.getClientByIdentification("1723456789");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto1, Objects.requireNonNull(response.getBody()).getData());
        verify(clientService, times(1)).getClientByIdentification("1723456789");
    }

    @Test
    @DisplayName("Crear un nuevo cliente: Debería crear y devolver el nuevo cliente con estado HTTP 201")
    void createClient() {
        when(clientService.createClient(any(ClientDto.class))).thenReturn(clientDto1);

        ResponseEntity<ApiResponse<ClientDto>> response = clientController.createClient(clientDto1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clientDto1, Objects.requireNonNull(response.getBody()).getData());
        verify(clientService, times(1)).createClient(any(ClientDto.class));
    }

    @Test
    @DisplayName("Actualizar un cliente existente: Debería actualizar y devolver el cliente actualizado con estado HTTP 200")
    void updateClient() {
        when(clientService.updateClient(anyLong(), any(UpdateClientDto.class))).thenReturn(clientDto1);

        UpdateClientDto updateClientDto = new UpdateClientDto();
        ResponseEntity<ApiResponse<ClientDto>> response = clientController.updateClient(1L, updateClientDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto1, Objects.requireNonNull(response.getBody()).getData());
        verify(clientService, times(1)).updateClient(anyLong(), any(UpdateClientDto.class));
    }

    @Test
    @DisplayName("Eliminar un cliente existente: Debería eliminar el cliente con estado HTTP 200")
    void deleteClient() {
        doNothing().when(clientService).deleteClient(1L);

        ResponseEntity<ApiResponse<Void>> response = clientController.deleteClient(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(clientService, times(1)).deleteClient(1L);
    }
}
