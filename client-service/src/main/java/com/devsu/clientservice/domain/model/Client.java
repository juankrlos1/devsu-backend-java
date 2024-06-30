package com.devsu.clientservice.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "person_id")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Client  extends Person {

    @Column(name = "client_id", unique = true, nullable = false)
    private Long clientId;

    private String password;
    private boolean status;

}
