package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.dto.AttributeDTO;
import com.tcn.meetandnote.entity.Attribute;
import com.tcn.meetandnote.services.impl.AttributeService;
import com.tcn.meetandnote.utils.FileUploadUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {

    private final AttributeService attributeService;
    private final ModelMapper modelMapper;

    public AttributeController(AttributeService attributeService, ModelMapper modelMapper) {
        this.attributeService = attributeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    public Attribute save(@RequestBody Attribute attribute) {
        return attributeService.save(attribute);
    }

    @GetMapping("/components/{id}")
    public AttributeDTO getAttributeByComponentId(@PathVariable long id) {
        return attributeService.getByComponentId(id);
    }

    @PostMapping("/upload/{id}")
    public String uploadFile(@PathVariable long id, @RequestPart MultipartFile file) {
        try {
            return attributeService.uploadFile(id, file);
        } catch (IOException e) {
            return "";
        }
    }

    @PutMapping("/{id}")
    public AttributeDTO updateTitle(@PathVariable long id, @RequestBody Attribute attribute) {
        return modelMapper.map(attributeService.updateTitle(id, attribute), AttributeDTO.class);
    }

}
