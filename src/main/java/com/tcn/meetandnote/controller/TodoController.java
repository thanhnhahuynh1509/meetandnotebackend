package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.dto.TodoDTO;
import com.tcn.meetandnote.entity.Attribute;
import com.tcn.meetandnote.entity.Todo;
import com.tcn.meetandnote.services.impl.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    private final ModelMapper modelMapper;


    public TodoController(TodoService todoService, ModelMapper modelMapper) {
        this.todoService = todoService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/attributes/{attributeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDTO save(@PathVariable long attributeId, @RequestBody Todo todo) {
        todo.setAttribute(new Attribute(attributeId));
        return modelMapper.map(todoService.save(todo), TodoDTO.class);
    }

    @GetMapping("/attributes/{attributeId}")
    public List<TodoDTO> getTodoByAttributeId(@PathVariable long attributeId) {
        List<Todo> todos = todoService.getByAttributeId(attributeId);
        return todos.stream().map(m -> modelMapper.map(m, TodoDTO.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        todoService.delete(id);
        return "OK";
    }

    @PutMapping("/{id}")
    public TodoDTO update(@PathVariable long id, @RequestBody Todo todo) {
        return modelMapper.map(todoService.update(id, todo), TodoDTO.class);
    }
}
