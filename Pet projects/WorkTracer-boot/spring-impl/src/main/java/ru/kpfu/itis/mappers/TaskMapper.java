package ru.kpfu.itis.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TaskResponse;
import ru.kpfu.itis.models.TaskEntity;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    TaskEntity toEntity(TaskRequest accountRequest);

    TaskResponse toResponse(TaskEntity accountEntity);
}
