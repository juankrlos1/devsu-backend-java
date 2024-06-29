package com.devsu.clientservice.infrastructure.persistence;

import com.devsu.clientservice.domain.model.Client;
import com.devsu.clientservice.domain.repository.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, Long>, ClientRepository {
    Optional<Client> findByPersonIdentification(String identification);
}