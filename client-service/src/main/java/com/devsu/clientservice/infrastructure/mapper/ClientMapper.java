package com.devsu.clientservice.infrastructure.mapper;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.UpdateClientDto;
import com.devsu.clientservice.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "clientId", source = "personId")
    ClientDto toDto(Client client);

    @Mapping(target = "personId", source = "clientId")
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "personId", ignore = true)
    void updateEntityFromDto(UpdateClientDto updateClientDto, @MappingTarget Client client);
}
