package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.AttributeDTO;
import com.tcn.meetandnote.dto.ComponentDTO;
import com.tcn.meetandnote.entity.*;
import com.tcn.meetandnote.repository.ComponentRepository;
import com.tcn.meetandnote.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComponentService extends BaseService<Component, Long> {


    private final ComponentRepository componentRepository;
    private final AttributeService attributeService;
    private final TodoService todoService;
    private final TypeService typeService;
    private final ModelMapper modelMapper;

    protected ComponentService(ComponentRepository baseRepository, AttributeService attributeService, TodoService todoService, TypeService typeService, ModelMapper modelMapper) {
        super(baseRepository, "component");
        this.componentRepository = baseRepository;
        this.attributeService = attributeService;
        this.todoService = todoService;
        this.typeService = typeService;
        this.modelMapper = modelMapper;
    }

    public ComponentDTO saveComponent(ComponentDTO model) {
        Component component = new Component();
        component.setPosX(model.getPosX());
        component.setPosY(model.getPosY());
        component.setUser(modelMapper.map(model.getUser(), User.class));
        component.setRoom(new Room(model.getParentId()));
        component.setType(typeService.getTypeByName(model.getType()));
        component = componentRepository.save(component);

        Attribute attribute = new Attribute();
        attribute.setComponent(component);
        attribute.setColor("#ffffff");
//        attribute.setContent(model.getAttribute().getContent());
//        attribute.setTitle(model.getAttribute().getTitle());
//        attribute.setColor(model.getAttribute().getColor());
//
        attribute = attributeService.save(attribute);
        AttributeDTO attributeDTO = modelMapper.map(attribute, AttributeDTO.class);

        if(model.getType().equals("TODO")) {
            Todo todo = new Todo();
            todo.setDone(false);
            todo.setAttribute(attribute);
            todo.setContent("");
            todoService.save(todo);
        }

        ComponentDTO componentDTO = modelMapper.map(component, ComponentDTO.class);
        componentDTO.setType(model.getType());
        componentDTO.setParentId(model.getParentId());
        componentDTO.setAttribute(attributeDTO);
        return componentDTO;
    }

    public List<ComponentDTO> getByRoomLink(String link) {
        List<Component> components = componentRepository.findComponentByRoomLink(link);
        return convertToDTOList(components);
    }

    public List<ComponentDTO> getByRoomLinkNotTrash(String link) {
        List<Component> components = componentRepository.findComponentByRoomLinkNotTrash(link);
        return convertToDTOList(components);
    }

    public List<ComponentDTO> getByRoomLinkTrash(String link) {
        List<Component> components = componentRepository.findComponentByRoomLinkTrash(link);
        return convertToDTOList(components);
    }

    private List<ComponentDTO> convertToDTOList(List<Component> components) {
        List<ComponentDTO> componentDTOS = new ArrayList<>();
        for(Component component : components) {
            ComponentDTO componentDTO = modelMapper.map(component, ComponentDTO.class);

            Attribute attribute = attributeService.getByComponentId(component.getId());
            AttributeDTO attributeDTO = modelMapper.map(attribute, AttributeDTO.class);

            componentDTO.setAttribute(attributeDTO);
            componentDTO.setParentId(component.getRoom().getId());
            componentDTO.setType(component.getType().getName());
            componentDTOS.add(componentDTO);
        }
        return componentDTOS;
    }

    @Override
    protected Component update(Long aLong, Component model) {
        return null;
    }

    public ComponentDTO updatePosition(Long id, ComponentDTO componentDTO) {
        Component component = getSingleResultById(id);
        component.setPosY(componentDTO.getPosY());
        component.setPosX(componentDTO.getPosX());
        componentRepository.save(component);
        return componentDTO;
    }

    public ComponentDTO trashComponent(long id) {
        Component component = getSingleResultById(id);
        component.setDeleted(true);
        return modelMapper.map(componentRepository.save(component), ComponentDTO.class);
    }

    public ComponentDTO reTrashComponent(long id) {
        Component component = getSingleResultById(id);
        component.setDeleted(false);
        return modelMapper.map(componentRepository.save(component), ComponentDTO.class);
    }

    public long getLastID() {
        Optional<Component> lastComponentOptional = componentRepository.findLastComponent();
        return lastComponentOptional.map(Component::getId).orElseGet(() -> 0L);
    }

    public void deleteByRoomId(long id) {
        todoService.deleteByRoomId(id);
        attributeService.deleteByRoomId(id);
        componentRepository.deleteByRoomId(id);
    }
}
