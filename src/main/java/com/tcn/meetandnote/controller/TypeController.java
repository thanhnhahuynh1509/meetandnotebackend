package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.entity.Type;
import com.tcn.meetandnote.services.impl.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/types")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    // Create
    @PostMapping("")
    public ResponseEntity<Type> save(@RequestBody Type type) {
        return new ResponseEntity<>(typeService.save(type), HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public List<Type> gets() {
        return typeService.gets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> get(@PathVariable long id) {
        return new ResponseEntity<>(typeService.get(id), HttpStatus.OK);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Type> update(@PathVariable long id, @RequestBody Type type) {
        return new ResponseEntity<>(typeService.update(id, type), HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        typeService.delete(id);
        return new ResponseEntity<>("Delete type with id: " + id + " successfully!", HttpStatus.OK);
    }

}
