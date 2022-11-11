package com.tcn.meetandnote.entity;

import javax.persistence.*;

@Entity
@Table(name = "attributes")
public class Attribute {

    private static final String PATH = "assets/attributes";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String color;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name = "file_type")
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    public Attribute() {
    }

    public Attribute(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Transient
    public String getFilePath() {
        return PATH + "/" + this.id + "/files";
    }
}
