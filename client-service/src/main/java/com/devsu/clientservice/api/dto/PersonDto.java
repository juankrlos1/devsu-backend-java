package com.devsu.clientservice.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PersonDto {
    private Long personId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^([MF])$", message = "Gender must be either 'M' or 'F'")
    private String gender;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 150, message = "Age must be at most 150")
    private Integer age;

    @NotBlank(message = "Identification is required")
    @Size(max = 20, message = "Identification must be at most 20 characters")
    private String identification;

    @NotBlank(message = "Address is required")
    @Size(max = 200, message = "Address must be at most 200 characters")
    private String address;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;
}