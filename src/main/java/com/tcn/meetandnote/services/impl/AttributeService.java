package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Attribute;
import com.tcn.meetandnote.repository.AttributeRepository;
import com.tcn.meetandnote.services.BaseService;
import org.springframework.stereotype.Service;

@Service
public class AttributeService extends BaseService<Attribute, Long> {

    private final AttributeRepository attributeRepository;
    private final TodoService todoService;

    protected AttributeService(AttributeRepository baseRepository, TodoService todoService) {
        super(baseRepository, "attribute");
        this.attributeRepository = baseRepository;
        this.todoService = todoService;
    }


    public void deleteByRoomId(long id) {
        attributeRepository.deleteByRoomId(id);
    }

    @Override
    public Attribute update(Long id, Attribute model) {
        Attribute attribute = getSingleResultById(id);
        attribute.setColor(model.getColor());
        attribute.setContent(model.getContent());
        attribute.setTitle(model.getTitle());
        return attributeRepository.save(attribute);
    }

    public Attribute getByComponentId(long id) {
        return attributeRepository.findByComponentId(id).orElseGet(() -> null);
    }
}
