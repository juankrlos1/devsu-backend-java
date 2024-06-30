package com.devsu.clientservice.domain.service;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.UpdateClientDto;
import com.devsu.clientservice.domain.model.Client;
import com.devsu.clientservice.domain.repository.ClientRepository;
import com.devsu.clientservice.exception.ClientNotFoundException;
import com.devsu.clientservice.exception.ClientAlreadyExistsException;
import com.devsu.clientservice.infrastructure.mapper.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientDto clientDto;
    private UpdateClientDto updateClientDto;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setClientId(1L);
        client.setName("Jose Lema");
        client.setIdentification("1723456789");
        client.setPassword("old_password");
        client.setStatus(false);
        client.setGender("M");
        client.setAge(35);
        client.setAddress("old address");
        client.setPhone("0987654321");

        clientDto = new ClientDto();
        clientDto.setClientId(1L);
        clientDto.setName("Jose Lema");
        clientDto.setIdentification("1723456789");

        updateClientDto = new UpdateClientDto();
        updateClientDto.setName("Updated Name");
        updateClientDto.setPassword("new_password");
        updateClientDto.setStatus(true);
        updateClientDto.setGender("M");
        updateClientDto.setAge(30);
        updateClientDto.setIdentification("new_identification");
        updateClientDto.setAddress("new address");
        updateClientDto.setPhone("123456789");
    }

    @Test
    @DisplayName("Obtener todos los clientes: Debería devolver una lista de todos los clientes")
    void getAllClients() {
        List<Client> clients = Arrays.asList(client, new Client());
        List<ClientDto> clientDtos = Arrays.asList(clientDto, new ClientDto());

        when(clientRepository.findAll()).thenReturn(clients);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto, new ClientDto());

        List<ClientDto> result = clientService.getAllClients();

        assertEquals(2, result.size());
        verify(clientRepository, times(1)).findAll();
        verify(clientMapper, times(2)).toDto(any(Client.class));
    }

    @Test
    @DisplayName("Obtener cliente por ID: Debería devolver el cliente correspondiente o lanzar ClientNotFoundException si no existe")
    void getClientById() {
        when(clientRepository.findByClientId(anyLong())).thenReturn(Optional.of(client));
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.getClientById(1L);

        assertEquals(clientDto, result);
        verify(clientRepository, times(1)).findByClientId(anyLong());
        verify(clientMapper, times(1)).toDto(any(Client.class));
    }

    @Test
    @DisplayName("Obtener cliente por ID: Debería lanzar ClientNotFoundException si el cliente no existe")
    void getClientByIdNotFound() {
        when(clientRepository.findByClientId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(1L));
        verify(clientRepository, times(1)).findByClientId(anyLong());
    }

    @Test
    @DisplayName("Obtener cliente por identificación: Debería devolver el cliente correspondiente o lanzar ClientNotFoundException si no existe")
    void getClientByIdentification() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.of(client));
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.getClientByIdentification("1723456789");

        assertEquals(clientDto, result);
        verify(clientRepository, times(1)).findByIdentification(anyString());
        verify(clientMapper, times(1)).toDto(any(Client.class));
    }

    @Test
    @DisplayName("Obtener cliente por identificación: Debería lanzar ClientNotFoundException si el cliente no existe")
    void getClientByIdentificationNotFound() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientByIdentification("1723456789"));
        verify(clientRepository, times(1)).findByIdentification(anyString());
    }

    @Test
    @DisplayName("Crear un nuevo cliente: Debería crear y devolver el nuevo cliente o lanzar ClientAlreadyExistsException si ya existe")
    void createClient() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.empty());
        when(clientMapper.toEntity(any(ClientDto.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.createClient(clientDto);

        assertEquals(clientDto, result);
        verify(clientRepository, times(1)).findByIdentification(anyString());
        verify(clientMapper, times(1)).toEntity(any(ClientDto.class));
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(clientMapper, times(1)).toDto(any(Client.class));
    }

    @Test
    @DisplayName("Crear un nuevo cliente: Debería lanzar ClientAlreadyExistsException si el cliente ya existe")
    void createClientAlreadyExists() {
        when(clientRepository.findByIdentification(anyString())).thenReturn(Optional.of(client));

        assertThrows(ClientAlreadyExistsException.class, () -> clientService.createClient(clientDto));
        verify(clientRepository, times(1)).findByIdentification(anyString());
    }

    @Test
    @DisplayName("Actualizar un cliente existente: Debería actualizar y devolver el cliente actualizado o lanzar ClientNotFoundException si no existe")
    void updateClient() {
        when(clientRepository.findByClientId(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.updateClient(1L, updateClientDto);

        assertEquals(clientDto, result);
        verify(clientRepository, times(1)).findByClientId(anyLong());
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(clientMapper, times(1)).toDto(any(Client.class));
    }

    @Test
    @DisplayName("Actualizar un cliente existente: Debería lanzar ClientNotFoundException si el cliente no existe")
    void updateClientNotFound() {
        when(clientRepository.findByClientId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(1L, updateClientDto));
        verify(clientRepository, times(1)).findByClientId(anyLong());
    }

    @Test
    @DisplayName("Eliminar un cliente existente: Debería eliminar el cliente o lanzar ClientNotFoundException si no existe")
    void deleteClient() {
        when(clientRepository.existsByClientId(anyLong())).thenReturn(true);
        doNothing().when(clientRepository).deleteByClientId(anyLong());

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).existsByClientId(anyLong());
        verify(clientRepository, times(1)).deleteByClientId(anyLong());
    }

    @Test
    @DisplayName("Eliminar un cliente existente: Debería lanzar ClientNotFoundException si el cliente no existe")
    void deleteClientNotFound() {
        when(clientRepository.existsByClientId(anyLong())).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> clientService.deleteClient(1L));
        verify(clientRepository, times(1)).existsByClientId(anyLong());
    }

    @Test
    @DisplayName("Actualizar campos de cliente: Debería actualizar todos los campos del cliente")
    void updateClientFields_AllFields() {
        when(clientRepository.findByClientId(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.updateClient(1L, updateClientDto);

        assertEquals(clientDto, result);
        assertEquals(updateClientDto.getName(), client.getName());
        assertEquals(updateClientDto.getPassword(), client.getPassword());
        assertEquals(updateClientDto.getStatus(), client.isStatus());
        assertEquals(updateClientDto.getGender(), client.getGender());
        assertEquals(updateClientDto.getAge(), client.getAge());
        assertEquals(updateClientDto.getIdentification(), client.getIdentification());
        assertEquals(updateClientDto.getAddress(), client.getAddress());
        assertEquals(updateClientDto.getPhone(), client.getPhone());
        verify(clientRepository, times(1)).findByClientId(anyLong());
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(clientMapper, times(1)).toDto(any(Client.class));
    }

    @Test
    @DisplayName("Actualizar campos de cliente: No debería actualizar campos nulos")
    void updateClientFields_NullFields() {
        updateClientDto.setName(null);
        updateClientDto.setPassword(null);
        updateClientDto.setStatus(null);
        updateClientDto.setGender(null);
        updateClientDto.setAge(null);
        updateClientDto.setIdentification(null);
        updateClientDto.setAddress(null);
        updateClientDto.setPhone(null);

        when(clientRepository.findByClientId(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        ClientDto result = clientService.updateClient(1L, updateClientDto);

        assertEquals(clientDto, result);
        assertEquals("Jose Lema", client.getName());
        assertEquals("old_password", client.getPassword());  // Suponiendo que el password era "old_password"
        assertFalse(client.isStatus());  // Suponiendo que el status era false
        assertEquals("M", client.getGender());  // Suponiendo que el gender era "M"
        assertEquals(35, client.getAge());  // Suponiendo que la edad era 35
        assertEquals("1723456789", client.getIdentification());
        assertEquals("old address", client.getAddress());  // Suponiendo que la dirección era "old address"
        assertEquals("0987654321", client.getPhone());  // Suponiendo que el teléfono era "0987654321"
        verify(clientRepository, times(1)).findByClientId(anyLong());
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(clientMapper, times(1)).toDto(any(Client.class));
    }
}
