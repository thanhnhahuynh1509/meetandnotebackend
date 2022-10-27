package com.tcn.meetandnote.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private Set<UserRoom> userRooms = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Room parent;

    @OneToMany(mappedBy = "parent")
    private Set<Room> children = new LinkedHashSet<>();


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

    public Set<UserRoom> getUserRooms() {
        return userRooms;
    }

    public void setUserRooms(Set<UserRoom> userRooms) {
        this.userRooms = userRooms;
    }

    public Room getParent() {
        return parent;
    }

    public void setParent(Room parent) {
        this.parent = parent;
    }

    public Set<Room> getChildren() {
        return children;
    }

    public void setChildren(Set<Room> children) {
        this.children = children;
    }
}
