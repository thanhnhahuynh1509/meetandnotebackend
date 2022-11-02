package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.dto.ComponentDTO;
import com.tcn.meetandnote.dto.RoomDTO;
import com.tcn.meetandnote.entity.Component;
import com.tcn.meetandnote.services.impl.ComponentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @PostMapping("")
    public ResponseEntity<ComponentDTO> save(@RequestBody ComponentDTO componentDTO) {
        return new ResponseEntity<>(componentService.saveComponent(componentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/link/{link}")
    public List<ComponentDTO> getByRoomLink(@PathVariable String link) {
        return componentService.getByRoomLink(link);
    }

    @GetMapping("/link-not-trash/{link}")
    public List<ComponentDTO> getByRoomLinkNotTrash(@PathVariable String link) {
        return componentService.getByRoomLinkNotTrash(link);
    }

    @GetMapping("/link-trash/{link}")
    public List<ComponentDTO> getByRoomLinkTrash(@PathVariable String link) {
        return componentService.getByRoomLinkTrash(link);
    }


    @PutMapping("/position/{id}")
    public ComponentDTO updatePosition(@PathVariable long id, @RequestBody ComponentDTO componentDTO) {
        return componentService.updatePosition(id, componentDTO);
    }

    @PutMapping("/trash/{id}")
    public ComponentDTO trashComponent(@PathVariable long id) {
        return componentService.trashComponent(id);
    }

    @DeleteMapping("/re-trash/{id}")
    public ComponentDTO reTrashComponent(@PathVariable long id) {
        return componentService.reTrashComponent(id);
    }

    @PutMapping("/{id}")
    public String delete(@PathVariable long id) {
        componentService.delete(id);
        return "ok";
    }
}
