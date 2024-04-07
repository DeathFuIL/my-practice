package ru.kpfu.itis.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.request.TargetRequest;
import ru.kpfu.itis.dto.response.TargetResponse;
import ru.kpfu.itis.models.TargetEntity;

@Mapper(componentModel = "spring")
public interface TargetMapper {

    @Mapping(target = "id", ignore = true)
    TargetEntity toEntity(TargetRequest targetRequest);


    TargetResponse toResponse(TargetEntity targetEntity);
}
