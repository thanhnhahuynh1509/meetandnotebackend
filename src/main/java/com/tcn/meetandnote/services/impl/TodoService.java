package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Todo;
import com.tcn.meetandnote.repository.TodoRepository;
import com.tcn.meetandnote.services.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService extends BaseService<Todo, Long> {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository baseRepository) {
        super(baseRepository, "todo");
        this.todoRepository = baseRepository;
    }

    @Override
    public Todo update(Long id, Todo model) {
        Todo todo = getSingleResultById(id);
        todo.setContent(model.getContent());
        todo.setDone(model.isDone());
        return todoRepository.save(todo);
    }

    public List<Todo> getByAttributeId(long attributeId) {
        return todoRepository.findByAttributeId(attributeId);
    }

    public void deleteByRoomId(long id) {
        todoRepository.deleteByRoomId(id);
    }
}
