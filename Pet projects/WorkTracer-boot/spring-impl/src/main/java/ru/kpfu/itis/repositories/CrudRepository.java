package ru.kpfu.itis.repositories;

import org.springframework.data.domain.Page;
import ru.kpfu.itis.dto.request.AccountRequest;
import ru.kpfu.itis.models.AccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudRepository<T> {

    UUID create(T model);

    Optional<T> findById(UUID uuid);

    List<T> getAll();

    Page<T> findAll();
    void update(UUID uuid, T model);

    void delete(UUID uuid);
}
