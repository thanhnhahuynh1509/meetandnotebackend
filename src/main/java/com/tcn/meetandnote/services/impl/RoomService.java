package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.RoomDTO;
import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.Type;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.RoomRepository;
import com.tcn.meetandnote.services.BaseService;
import com.tcn.meetandnote.utils.MD5Hashing;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService extends BaseService<Room, Long> {

    private final RoomRepository roomRepository;
    private final ComponentService componentService;
    private final TypeService typeService;

    public RoomService(RoomRepository roomRepository, ComponentService componentService, TypeService typeService) {
        super(roomRepository, "room");
        this.roomRepository = roomRepository;
        this.componentService = componentService;
        this.typeService = typeService;
    }

    @Override
    public Room save(Room room) {
        Type type = typeService.getTypeByName("ROOM");
        room.setType(type);
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

    public Room getByLink(String link) {
        return roomRepository.findByLink(link).orElseThrow(() -> new NotFoundException("Not found room with link: " + link));
    }

    @Override
    protected Room update(Long id, Room model) {
        return null;
    }

    public Room updatePosition(Long id, RoomDTO model) {
        Room room = getSingleResultById(id);
        room.setPosY(model.getPosY());
        room.setPosX(model.getPosX());
        return roomRepository.save(room);
    }

    public Room updateInformation(Long id, RoomDTO model) {
        Room room = getSingleResultById(id);
        room.setTitle(model.getTitle());
        room.setIcon(model.getIcon());
        room.setColor(model.getColor());
        return roomRepository.save(room);
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

    public long getLastID() {
        Optional<Room> lastRoomOptional = roomRepository.findLastRoom();
        return lastRoomOptional.map(Room::getId).orElseGet(() -> 0L);
    }

}
