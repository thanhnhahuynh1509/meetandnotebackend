package com.tcn.meetandnote.services;

import com.tcn.meetandnote.entity.Type;

import java.util.List;

public interface TypeService {
    Type save(Type type);
    Type get(long id);
    List<Type> gets();
    Type update(long id, Type type);
    void delete(long id);
}
