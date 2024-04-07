package ru.kpfu.itis.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.dto.request.CompanyRequest;
import ru.kpfu.itis.dto.response.CompanyResponse;
import ru.kpfu.itis.models.CompanyEntity;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    CompanyEntity toEntity(CompanyRequest accountRequest);

    CompanyResponse toResponse(CompanyEntity accountEntity);
}
