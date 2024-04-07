package ru.kpfu.itis.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.dto.request.MessageRequest;
import ru.kpfu.itis.dto.response.MessageResponse;
import ru.kpfu.itis.models.MessageEntity;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    MessageEntity toEntity(MessageRequest messageRequest);

    MessageResponse toResponse(MessageEntity messageEntity);
}
