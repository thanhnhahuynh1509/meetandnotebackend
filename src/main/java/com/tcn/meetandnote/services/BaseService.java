package com.tcn.meetandnote.services;

import com.tcn.meetandnote.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<T, ID> {

    protected final JpaRepository<T, ID> baseRepository;
    private final String serviceName;

    protected BaseService(JpaRepository<T, ID> baseRepository, String serviceName) {
        this.baseRepository = baseRepository;
        this.serviceName = serviceName;
    }

    protected T getSingleResultById(ID id) {
        return baseRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found " + serviceName + " with id: " + id));
    }

    public T save(T model) {
        return baseRepository.save(model);
    }

    public List<T> gets() {
        return baseRepository.findAll();
    }

    public T get(ID id) {
        return getSingleResultById(id);
    }

    protected abstract T update(ID id, T model);

    public void delete(ID id) {
        getSingleResultById(id);
        baseRepository.deleteById(id);
    }
}


