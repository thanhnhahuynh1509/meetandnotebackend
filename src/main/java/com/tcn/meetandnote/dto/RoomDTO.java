package com.tcn.meetandnote.dto;

public class RoomDTO {

    private long id;

    private String title;

    private String readToken = "";

    private String fullPermissionToken = "";

    private String link;

    private String icon;

    private String color;

    private double posX;

    private double posY;

    private String type;

    private long parentId;

    private UserDTO user;

    private UserDTO owner;

    private UserDTO userCreated;

    private String createdDate;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public UserDTO getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(UserDTO userCreated) {
        this.userCreated = userCreated;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
