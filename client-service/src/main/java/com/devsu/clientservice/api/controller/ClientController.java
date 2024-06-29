package com.devsu.clientservice.api.controller;

import com.devsu.clientservice.api.dto.UpdateClientDto;
import com.devsu.clientservice.common.dto.ApiResponse;
import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.domain.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientDto>>> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(createSuccessResponse("Clients retrieved successfully", clients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDto>> getClientById(@PathVariable Long id) {
        ClientDto client = clientService.getClientById(id);
        return ResponseEntity.ok(createSuccessResponse("Client retrieved successfully", client));
    }

    @GetMapping("/identification/{identification}")
    public ResponseEntity<ApiResponse<ClientDto>> getClientByIdentification(@PathVariable @NotBlank String identification) {
        ClientDto client = clientService.getClientByIdentification(identification);
        return ResponseEntity.ok(createSuccessResponse("Client retrieved successfully", client));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClientDto>> createClient(@Valid @RequestBody ClientDto clientDto) {
        ClientDto createdClient = clientService.createClient(clientDto);
        return new ResponseEntity<>(createSuccessResponse("Client created successfully", createdClient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDto>> updateClient(@PathVariable Long id, @Valid @RequestBody UpdateClientDto updateClientDto) {
        ClientDto updatedClient = clientService.updateClient(id, updateClientDto);
        return ResponseEntity.ok(createSuccessResponse("Client updated successfully", updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok(createSuccessResponse("Client deleted successfully", null));
    }

    private <T> ApiResponse<T> createSuccessResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
