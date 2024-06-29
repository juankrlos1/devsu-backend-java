package com.devsu.clientservice.domain.service;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.UpdateClientDto;
import com.devsu.clientservice.domain.model.Client;
import com.devsu.clientservice.domain.repository.ClientRepository;
import com.devsu.clientservice.exception.ClientNotFoundException;
import com.devsu.clientservice.exception.ClientAlreadyExistsException;
import com.devsu.clientservice.infrastructure.messaging.RabbitMQSender;
import com.devsu.clientservice.infrastructure.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final RabbitMQSender rabbitMQSender;

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
        return clientRepository.findByIdentification(identification)
                .map(clientMapper::toDto)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with identification: " + identification));
    }

    @Override
    @Transactional
    public ClientDto createClient(ClientDto clientDto) {
        clientRepository.findByIdentification(clientDto.getIdentification())
                .ifPresent(p -> {
                    throw new ClientAlreadyExistsException("Client already exists with identification: " + clientDto.getIdentification());
                });
        Client client = clientMapper.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        ClientDto clientDtoResponse = clientMapper.toDto(savedClient);
        rabbitMQSender.send(clientDto);
        return clientDtoResponse;
    }

    @Override
    @Transactional
    public ClientDto updateClient(Long id, UpdateClientDto updateClientDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));

        log.info("before update existingClient map: {}", existingClient.toString());

        updateClientFields(existingClient, updateClientDto);
        log.info("existingClient map: {}", existingClient.toString());
        Client savedClient = clientRepository.save(existingClient);
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

    private void updateClientFields(Client existingClient, UpdateClientDto updateClientDto) {
        if (updateClientDto.getPassword() != null) {
            existingClient.setPassword(updateClientDto.getPassword());
        }
        if (updateClientDto.getStatus() != null) {
            existingClient.setStatus(updateClientDto.getStatus());
        }
        if (updateClientDto.getName() != null) {
            existingClient.setName(updateClientDto.getName());
        }
        if (updateClientDto.getGender() != null) {
            existingClient.setGender(updateClientDto.getGender());
        }
        if (updateClientDto.getAge() != null) {
            existingClient.setAge(updateClientDto.getAge());
        }
        if (updateClientDto.getIdentification() != null) {
            existingClient.setIdentification(updateClientDto.getIdentification());
        }
        if (updateClientDto.getAddress() != null) {
            existingClient.setAddress(updateClientDto.getAddress());
        }
        if (updateClientDto.getPhone() != null) {
            existingClient.setPhone(updateClientDto.getPhone());
        }
    }
}
