package com.tcn.meetandnote.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private double posX;
    private double posY;

    private String link;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<UserRoom> userRooms = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Room parent;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne
    @JoinColumn(name = "user_created_id")
    private User userCreated;

    private String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Room> children = new LinkedHashSet<>();

    public Room() {

    }

    public Room(long id) {
        this.id = id;
    }

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
