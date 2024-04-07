package ru.kpfu.itis.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.dto.request.DialogRequest;
import ru.kpfu.itis.dto.response.DialogResponse;
import ru.kpfu.itis.models.DialogEntity;

@Mapper(componentModel = "spring")
public interface DialogMapper {

    @Mapping(target = "id", ignore = true)
    DialogEntity toEntity(DialogRequest dialogRequest);

    DialogResponse toResponse(DialogEntity dialogEntity);

}
