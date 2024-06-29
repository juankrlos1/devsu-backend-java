package com.devsu.clientservice.infrastructure.mapper;

import com.devsu.clientservice.api.dto.ClientDto;
import com.devsu.clientservice.api.dto.PersonDto;
import com.devsu.clientservice.domain.model.Client;
import com.devsu.clientservice.domain.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "person", source = "person")
    ClientDto toDto(Client client);

    @Mapping(target = "person", source = "person")
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "clientId", ignore = true)
    Client updateEntityFromDto(ClientDto clientDto, @MappingTarget Client client);

    PersonDto toPersonDto(Person person);

    Person toPersonEntity(PersonDto personDto);
}
