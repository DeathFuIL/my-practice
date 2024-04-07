package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.request.AddressRequest;
import ru.kpfu.itis.dto.response.AddressResponse;
import ru.kpfu.itis.exceptions.service.notfound.AddressNotFoundException;
import ru.kpfu.itis.mappers.AddressMapper;
import ru.kpfu.itis.repositories.AddressRepository;
import ru.kpfu.itis.services.AddressService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    private final AddressMapper mapper;

    @Override
    public AddressResponse getById(UUID uuid) {
        return mapper.toResponse(
                repository.findById(uuid)
                        .orElseThrow(() -> new AddressNotFoundException(uuid))
        );
    }

    @Override
    public Set<AddressResponse> getAll() {
        return repository.getAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(AddressRequest request) {
        return repository.create(
                mapper.toEntity(request)
        );
    }

    @Override
    public void update(UUID uuid, AddressRequest request) {
        throw new UnsupportedOperationException("This method must not be used in this class");
    }

    @Override
    public void delete(UUID uuid) {
        throw new UnsupportedOperationException("This method must not be used in this class");
    }
}
