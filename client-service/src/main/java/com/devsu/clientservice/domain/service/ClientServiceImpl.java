package com.devsu.clientservice.domain.service;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.domain.model.Client;
import com.devsu.clientservice.domain.repository.ClientRepository;
import com.devsu.clientservice.exception.ClientNotFoundException;
import com.devsu.clientservice.exception.ClientAlreadyExistsException;
import com.devsu.clientservice.infrastructure.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDto)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientByIdentification(String identification) {
        return clientRepository.findByPersonIdentification(identification)
                .map(clientMapper::toDto)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with identification: " + identification));
    }

    @Override
    @Transactional
    public ClientDto createClient(ClientDto clientDto) {
        clientRepository.findByPersonIdentification(clientDto.getPerson().getIdentification())
                .ifPresent(p -> {
                    throw new ClientAlreadyExistsException("Client already exists with identification: " + clientDto.getPerson().getIdentification());
                });
        Client client = clientMapper.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    @Override
    @Transactional
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));

        Client updatedClient = clientMapper.updateEntityFromDto(clientDto, existingClient);
        Client savedClient = clientRepository.save(updatedClient);
        return clientMapper.toDto(savedClient);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
}
