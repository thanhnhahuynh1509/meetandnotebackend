package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.ComponentDTO;
import com.tcn.meetandnote.entity.Component;
import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.repository.ComponentRepository;
import com.tcn.meetandnote.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentService extends BaseService<Component, Long> {


    private final ComponentRepository componentRepository;
    private final RoomService roomService;
    private final TypeService typeService;
    private final ModelMapper modelMapper;

    protected ComponentService(ComponentRepository baseRepository, RoomService roomService, TypeService typeService, ModelMapper modelMapper) {
        super(baseRepository, "component");
        this.componentRepository = baseRepository;
        this.roomService = roomService;
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
        ComponentDTO componentDTO = modelMapper.map(component, ComponentDTO.class);
        componentDTO.setType(model.getType());
        componentDTO.setParentId(model.getParentId());
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
}
