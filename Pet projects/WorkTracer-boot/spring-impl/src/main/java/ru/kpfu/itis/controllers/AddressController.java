package ru.kpfu.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.api.AddressAPI;
import ru.kpfu.itis.dto.request.AddressRequest;
import ru.kpfu.itis.dto.response.AddressResponse;
import ru.kpfu.itis.services.AddressService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressController implements AddressAPI {

    private final AddressService service;

    @Override
    public AddressResponse getById(UUID uuid) {
        return service.getById(uuid);
    }

    @Override
    public Set<AddressResponse> getAll() {
        return service.getAll();
    }

    @Override
    public UUID create(AddressRequest addressRequest) {
        return service.create(addressRequest);
    }
}
