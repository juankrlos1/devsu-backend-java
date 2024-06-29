package com.devsu.clientservice.domain.service;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.UpdateClientDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getAllClients();
    ClientDto getClientById(Long id);
    ClientDto getClientByIdentification(String identification);
    ClientDto createClient(ClientDto clientDto);
    ClientDto updateClient(Long id, UpdateClientDto clientDto);
    void deleteClient(Long id);
}
