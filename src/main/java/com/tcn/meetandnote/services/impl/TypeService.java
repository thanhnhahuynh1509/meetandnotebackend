package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Type;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.TypeRepository;
import com.tcn.meetandnote.services.BaseService;
import org.springframework.stereotype.Service;

@Service
public class TypeService extends BaseService<Type, Long> {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        super(typeRepository, "type");
        this.typeRepository = typeRepository;
    }

    @Override
    public Type update(Long id, Type type) {
        Type typeDb = getSingleResultById(id);
        typeDb.setDescription(type.getDescription());
        typeDb.setName(type.getName());
        return typeRepository.save(typeDb);
    }

    public Type getTypeByName(String name) {
        return typeRepository.findTypeByName(name).orElseThrow(() -> new NotFoundException("Not found type with name: " + name));
    }

}
