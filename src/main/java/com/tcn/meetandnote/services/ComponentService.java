package com.tcn.meetandnote.services;

import com.tcn.meetandnote.entity.Component;

import java.util.List;

public interface ComponentService {
    Component save(Component component);
    Component get(long id);
    List<Component> gets();
    Component update(long id, Component component);
    void delete(long id);
}
