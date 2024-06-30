package com.devsu.clientservice.infrastructure.mapper;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.UpdateClientDto;
import com.devsu.clientservice.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "personId", source = "personId")
    ClientDto toDto(Client client);

    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "personId", source = "personId")
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "personId", ignore = true)
    void updateEntityFromDto(UpdateClientDto updateClientDto, @MappingTarget Client client);
}