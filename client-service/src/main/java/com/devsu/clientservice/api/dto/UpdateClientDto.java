package com.devsu.clientservice.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateClientDto {

    private String password;
    private Boolean status;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;

}
