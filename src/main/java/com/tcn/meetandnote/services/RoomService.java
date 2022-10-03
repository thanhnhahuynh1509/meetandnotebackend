package com.tcn.meetandnote.services;

import com.tcn.meetandnote.entity.Room;

import java.util.List;

public interface RoomService {
    Room save(Room room);
    Room get(long id);
    List<Room> gets();
    Room update(String link, String fullPermissionToken, Room room);
    void delete(String link, String fullPermissionToken);
}
