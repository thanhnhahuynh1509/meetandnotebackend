package com.tcn.meetandnote.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String icon = "";
    @Column(name = "read_token", length = 50, nullable = false)
    private String readToken = "";

    @Column(name = "full_permission_token", nullable = false, length = 50)
    private String fullPermissionToken = "";

    private String link;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<UserRoom> userRooms = new ArrayList<>();


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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<UserRoom> getUserRooms() {
        return userRooms;
    }

    public void setUserRooms(List<UserRoom> userRooms) {
        this.userRooms = userRooms;
    }
}
