package com.tcn.meetandnote.dto;

public class UserDTO {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    private String roomLink;
    private boolean enabled;

    private boolean fullPermission;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRoomLink() {
        return roomLink;
    }

    public void setRoomLink(String roomLink) {
        this.roomLink = roomLink;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFullPermission() {
        return fullPermission;
    }

    public void setFullPermission(boolean fullPermission) {
        this.fullPermission = fullPermission;
    }
}
