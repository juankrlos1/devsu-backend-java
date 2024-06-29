package com.devsu.financeservice.infrastructure.external;

import com.devsu.financeservice.api.dto.ClientDto;
import com.devsu.financeservice.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-service", url = "${client-service.url}")
public interface ClientServiceClient {

    @GetMapping("/api/clients/{clientId}")
    ApiResponse<ClientDto> getClientById(@PathVariable("clientId") Long clientId);
}
