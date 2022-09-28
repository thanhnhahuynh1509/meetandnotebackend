package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.RoomRepository;
import com.tcn.meetandnote.services.RoomService;
import com.tcn.meetandnote.utils.MD5Hashing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room save(Room room) {
        room = roomRepository.save(room);
        String readToken = MD5Hashing.hash(room+"read");
        String fullPermissionToken = MD5Hashing.hash(room+"full");

        room.setReadToken(readToken);
        room.setFullPermissionToken(fullPermissionToken);

        return roomRepository.save(room);
    }

    @Override
    public Room get(long id) {
        return getRoomById(id);
    }

    @Override
    public List<Room> gets() {
        return roomRepository.findAll();
    }

    @Override
    public Room update(String fullPermissionToken, Room room) {
        Room db = getRoomByFullPermissionToken(fullPermissionToken);
        db.setColor(room.getColor());
        db.setIcon(room.getIcon());
        db.setTitle(room.getTitle());
        return roomRepository.save(db);
    }

    @Override
    public void delete(String fullPermissionToken) {
        Room room = getRoomByFullPermissionToken(fullPermissionToken);
        roomRepository.delete(room);
    }

    private Room getRoomById(long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found room with id: " + id));
    }

    private Room getRoomByFullPermissionToken(String fullPermissionToken) {
        return roomRepository.findByFullPermissionToken(fullPermissionToken).orElseThrow(() -> new NotFoundException("Not found room with token: " + fullPermissionToken));
    }
}
