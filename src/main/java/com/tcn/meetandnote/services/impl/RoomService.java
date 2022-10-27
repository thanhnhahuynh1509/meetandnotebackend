package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.RoomRepository;
import com.tcn.meetandnote.services.BaseService;
import com.tcn.meetandnote.utils.MD5Hashing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService extends BaseService<Room, Long> {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        super(roomRepository, "room");
        this.roomRepository = roomRepository;
    }

    @Override
    public Room save(Room room) {
        room = roomRepository.save(room);
        String link = MD5Hashing.hash(room.getId() + "");
        String readToken = MD5Hashing.hash(room.getId()+"read");
        String fullPermissionToken = MD5Hashing.hash(room.getId()+"full");

        room.setReadToken(readToken);
        room.setFullPermissionToken(fullPermissionToken);
        room.setLink(link);

        return roomRepository.save(room);
    }


    @Override
    public List<Room> gets() {
        return roomRepository.findAll();
    }

    @Override
    protected Room update(Long id, Room model) {
        return null;
    }

    public Room update(String link, String fullPermissionToken, Room room) {
        Room db = getRoomByFullPermissionToken(link, fullPermissionToken);
        db.setColor(room.getColor());
        db.setIcon(room.getIcon());
        db.setTitle(room.getTitle());
        return roomRepository.save(db);
    }

    public void delete(String link, String fullPermissionToken) {
        Room room = getRoomByFullPermissionToken(link, fullPermissionToken);
        roomRepository.delete(room);
    }

    private Room getRoomByFullPermissionToken(String link, String fullPermissionToken) {
        return roomRepository.findByLinkAndFullPermissionToken(link, fullPermissionToken)
                .orElseThrow(() -> new NotFoundException("Not found room with token: " + fullPermissionToken));
    }
}
