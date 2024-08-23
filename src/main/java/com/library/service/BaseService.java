package com.library.service;

import java.util.List;
import java.util.Optional;


public interface BaseService<T, ID> {

    List<T> getAll();

    Optional<T> getById(ID id);

    T create(T entity);

    T update(ID id, T entityDetails);

    void delete(ID id);
}

