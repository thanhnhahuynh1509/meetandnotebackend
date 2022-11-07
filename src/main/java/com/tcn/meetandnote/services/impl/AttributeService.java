package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.AttributeDTO;
import com.tcn.meetandnote.dto.TodoDTO;
import com.tcn.meetandnote.entity.Attribute;
import com.tcn.meetandnote.entity.Todo;
import com.tcn.meetandnote.repository.AttributeRepository;
import com.tcn.meetandnote.services.BaseService;
import com.tcn.meetandnote.utils.FileUploadUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeService extends BaseService<Attribute, Long> {

    private final AttributeRepository attributeRepository;
    private final ModelMapper modelMapper;
    private final TodoService todoService;

    protected AttributeService(AttributeRepository baseRepository, ModelMapper modelMapper, TodoService todoService) {
        super(baseRepository, "attribute");
        this.attributeRepository = baseRepository;
        this.modelMapper = modelMapper;
        this.todoService = todoService;
    }



    @Override
    public Attribute update(Long id, Attribute model) {
        Attribute attribute = getSingleResultById(id);
        attribute.setColor(model.getColor());
        attribute.setContent(model.getContent());
        attribute.setTitle(model.getTitle());
        return attributeRepository.save(attribute);
    }

    public AttributeDTO getByComponentId(long id) {
        Attribute attribute = attributeRepository.findByComponentId(id).orElseGet(() -> null);
        AttributeDTO attributeDTO = modelMapper.map(attribute, AttributeDTO.class);
        List<Todo> todos = todoService.getByAttributeId(attribute.getId());

        for(Todo todo: todos) {
            attributeDTO.getTodos().add(modelMapper.map(todo, TodoDTO.class));
        }

        return attributeDTO;
    }

    public String uploadFile(long id, MultipartFile file) throws IOException {
        Attribute attribute = getSingleResultById(id);

        // delete old file
        if(attribute.getContent() != null && !attribute.getContent().isBlank()) {
            FileUploadUtils.delete(attribute.getContent());
        }

        //
        File fileCheck = new File(attribute.getFilePath());
        if(!fileCheck.exists()) {
            Files.createDirectories(Paths.get(attribute.getFilePath()));
        }
        String filePath = fileCheck.getAbsolutePath();
        FileUploadUtils.upload(file.getOriginalFilename(), filePath, file);
        attribute.setTitle(file.getOriginalFilename());
        attribute.setFileType(file.getContentType());
        attribute.setContent(attribute.getFilePath() + "/" + file.getOriginalFilename());
        attribute = attributeRepository.save(attribute);
        return attribute.getContent();
    }

    public Attribute updateTitle(long id, Attribute model) {
        Attribute attribute = getSingleResultById(id);
        attribute.setTitle(model.getTitle());
        return attributeRepository.save(attribute);
    }
}
