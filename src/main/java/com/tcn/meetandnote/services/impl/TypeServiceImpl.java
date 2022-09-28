package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Type;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.TypeRepository;
import com.tcn.meetandnote.services.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type get(long id) {
        return getTypeById(id);
    }

    @Override
    public List<Type> gets() {
        return typeRepository.findAll();
    }

    @Override
    public Type update(long id, Type type) {
        Type typeDb = getTypeById(id);
        typeDb.setDescription(type.getDescription());
        typeDb.setName(type.getName());
        return typeRepository.save(typeDb);
    }

    @Override
    public void delete(long id) {
        Type type = getTypeById(id);
        typeRepository.delete(type);
    }

    private Type getTypeById(long id) {
        return typeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found type with id: " + id));
    }
}
