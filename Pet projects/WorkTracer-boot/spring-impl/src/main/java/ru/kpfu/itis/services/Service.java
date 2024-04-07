package ru.kpfu.itis.services;

import java.util.Set;
import java.util.UUID;

public interface Service<Q, R> {

    R getById(UUID uuid);

    Set<R> getAll();

    UUID create(Q request);

    void update(UUID uuid, Q request);

    void delete(UUID uuid);

}
