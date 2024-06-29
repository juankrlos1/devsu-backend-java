package com.devsu.clientservice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long clientId;

    @NotBlank
    private String password;

    private boolean status;

    @NotNull
    private PersonDto person;

}
