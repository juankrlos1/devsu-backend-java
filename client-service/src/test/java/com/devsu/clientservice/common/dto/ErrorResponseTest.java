package com.devsu.clientservice.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    @DisplayName("Crear ErrorResponse: Debería crear un ErrorResponse con todos los campos")
    void createErrorResponse_AllFields() {
        LocalDateTime timestamp = LocalDateTime.now();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(true)
                .message("Error occurred")
                .details("Error details")
                .timestamp(timestamp)
                .path("/api/error")
                .build();

        assertEquals(true, errorResponse.getStatus());
        assertEquals("Error occurred", errorResponse.getMessage());
        assertEquals("Error details", errorResponse.getDetails());
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals("/api/error", errorResponse.getPath());
    }

    @Test
    @DisplayName("Crear ErrorResponse: Debería tener campos no nulos por defecto")
    void createErrorResponse_DefaultFields() {
        ErrorResponse errorResponse = ErrorResponse.builder().status(false).build();

        assertNotNull(errorResponse);
        assertEquals(false, errorResponse.getStatus());
        assertNull(errorResponse.getMessage());
        assertNull(errorResponse.getDetails());
        assertNull(errorResponse.getTimestamp());
        assertNull(errorResponse.getPath());
    }
}
