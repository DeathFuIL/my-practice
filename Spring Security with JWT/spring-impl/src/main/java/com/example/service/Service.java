package com.example.service;

import java.util.List;
import java.util.UUID;

public interface Service<Q, R> {

    List<R> getAll();

    R getById(UUID id);

    UUID create(Q model);


}
