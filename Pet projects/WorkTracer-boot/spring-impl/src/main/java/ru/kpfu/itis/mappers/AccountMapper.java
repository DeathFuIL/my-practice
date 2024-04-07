package ru.kpfu.itis.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.request.AccountRequest;
import ru.kpfu.itis.dto.response.AccountResponse;
import ru.kpfu.itis.models.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", source = "accountRequest.addressRequest")
    AccountEntity toEntity(AccountRequest accountRequest);

    AccountResponse toResponse(AccountEntity accountEntity);
}
