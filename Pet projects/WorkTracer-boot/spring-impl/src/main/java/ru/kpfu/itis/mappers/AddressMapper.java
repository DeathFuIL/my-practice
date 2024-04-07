package ru.kpfu.itis.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.dto.request.AddressRequest;
import ru.kpfu.itis.dto.response.AddressResponse;
import ru.kpfu.itis.models.AddressEntity;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    AddressEntity toEntity(AddressRequest accountRequest);

    AddressResponse toResponse(AddressEntity accountEntity);
}
