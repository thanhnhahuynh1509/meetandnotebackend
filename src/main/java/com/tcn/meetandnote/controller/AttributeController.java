package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.entity.Attribute;
import com.tcn.meetandnote.services.impl.AttributeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping("")
    public Attribute save(@RequestBody Attribute attribute) {
        return attributeService.save(attribute);
    }

    @GetMapping("/components/{id}")
    public Attribute getAttributeByComponentId(@PathVariable long id) {
        return attributeService.getByComponentId(id);
    }
}
