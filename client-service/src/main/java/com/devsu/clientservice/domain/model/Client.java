package com.devsu.clientservice.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@PrimaryKeyJoinColumn(name = "clientId")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client  extends Person {

    private String password;
    private boolean status;

}
