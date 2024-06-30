package com.devsu.clientservice.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    @DisplayName("Crear ApiResponse: Debería crear un ApiResponse con todos los campos")
    void createApiResponse_AllFields() {
        LocalDateTime timestamp = LocalDateTime.now();
        String data = "Test data";

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .success(true)
                .message("Operation successful")
                .data(data)
                .timestamp(timestamp)
                .build();

        assertTrue(apiResponse.isSuccess());
        assertEquals("Operation successful", apiResponse.getMessage());
        assertEquals(data, apiResponse.getData());
        assertEquals(timestamp, apiResponse.getTimestamp());
    }

    @Test
    @DisplayName("Crear ApiResponse: Debería tener campos no nulos por defecto")
    void createApiResponse_DefaultFields() {
        ApiResponse<String> apiResponse = ApiResponse.<String>builder().build();

        assertNotNull(apiResponse);
        assertFalse(apiResponse.isSuccess());
        assertNull(apiResponse.getMessage());
        assertNull(apiResponse.getData());
        assertNull(apiResponse.getTimestamp());
    }
}
