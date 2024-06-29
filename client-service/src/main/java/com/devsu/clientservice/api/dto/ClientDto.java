package com.devsu.clientservice.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto extends PersonDto {

    private Long clientId;

    @NotBlank
    private String password;

    private boolean status;

}
