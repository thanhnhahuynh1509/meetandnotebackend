package com.tcn.meetandnote.entity;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 250, nullable = false)
    private String title;

    @Column(nullable = false)
    private String color = "#ffffff";
    private String icon;
    @Column(name = "read_token", length = 50, nullable = false)
    private String readToken = "";

    @Column(name = "full_permission_token", nullable = false, length = 50)
    private String fullPermissionToken = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getReadToken() {
        return readToken;
    }

    public void setReadToken(String readToken) {
        this.readToken = readToken;
    }

    public String getFullPermissionToken() {
        return fullPermissionToken;
    }

    public void setFullPermissionToken(String fullPermissionToken) {
        this.fullPermissionToken = fullPermissionToken;
    }
}
