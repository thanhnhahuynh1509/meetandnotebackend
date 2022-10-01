package com.tcn.meetandnote.entity;

import javax.persistence.*;

@Entity
@Table(name = "users_rooms")
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "is_owner")
    private boolean isOwner;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "is_full_permission")
    private boolean isFullPermission;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isFullPermission() {
        return isFullPermission;
    }

    public void setFullPermission(boolean fullPermission) {
        isFullPermission = fullPermission;
    }
}
