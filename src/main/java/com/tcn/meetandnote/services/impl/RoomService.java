package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.ComponentDTO;
import com.tcn.meetandnote.dto.RoomDTO;
import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.Type;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.RoomRepository;
import com.tcn.meetandnote.repository.UserRepository;
import com.tcn.meetandnote.repository.UserRoomRepository;
import com.tcn.meetandnote.services.BaseService;
import com.tcn.meetandnote.utils.MD5Hashing;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService extends BaseService<Room, Long> {

    private final RoomRepository roomRepository;
    private final ComponentService componentService;
    private final UserRoomService userRoomService;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final TypeService typeService;
    private final ModelMapper modelMapper;

    public RoomService(RoomRepository roomRepository, ComponentService componentService, UserRoomService userRoomService, UserRepository userRepository, UserRoomRepository userRoomRepository, TypeService typeService, ModelMapper modelMapper) {
        super(roomRepository, "room");
        this.roomRepository = roomRepository;
        this.componentService = componentService;
        this.userRoomService = userRoomService;
        this.userRepository = userRepository;
        this.userRoomRepository = userRoomRepository;
        this.typeService = typeService;
        this.modelMapper = modelMapper;
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

    public List<RoomDTO> getRoomByOwner(long userId) {
        List<Room> rooms = roomRepository.findRoomByOwner(userId);
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for(Room room : rooms) {
            roomDTOS.add(convertDTO(room));
        }
        return roomDTOS;
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

    public List<RoomDTO> getRoomByUserId(long id) {
        List<RoomDTO> roomDTOS = new ArrayList<>();
        try {
            List<Room> rooms = userRoomService.getRoomsByUserId(id);
            for(Room room : rooms) {
                roomDTOS.add(convertDTO(room));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return roomDTOS;
    }

    public String inviteUser(long roomId, String email, String permission) {
        try {
            User user = userRepository.findUserByUsername(email).orElseThrow(() -> new NotFoundException("Not found user"));
            Room room = getSingleResultById(roomId);

            UserRoom userRoomCheck = userRoomRepository
                    .findUserRoomByUserIDAndRoomID(user.getId(), room.getId()).orElseGet(() -> null);

            if(userRoomCheck != null) {
                return "EXISTS";
            }

            UserRoom userRoom = new UserRoom();
            userRoom.setRoom(room);
            userRoom.setOwner(false);
            if(permission.equalsIgnoreCase("FULL")) {
                userRoom.setFullPermission(true);
            }
            userRoom.setRead(true);
            userRoom.setUser(user);
            userRoomService.save(userRoom);
        } catch (NotFoundException ex) {
            return "NOT OK";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "BAD REQUEST";
        }

        return "OK";
    }

    public String leaveRoom(long roomId, long userId) {
        UserRoom userRoom = userRoomService.getUserRoomByUserIDAndRoomID(userId, roomId);
        if(userRoom == null)
            return "NOT OK";
        userRoomService.delete(userRoom.getId());
        return "OK";
    }

    public RoomDTO getRoomOwnerByUserAndLink(String link, long userId) {
        Optional<Room> roomOptional = userRoomRepository.findRoomByOwnerAndRoomLinkAndUserId(link, userId);
        if(roomOptional.isEmpty())
            return null;

        return convertDTO(roomOptional.get());
    }

    public boolean checkUserInRoom(String link, long id) {
        return userRoomRepository.findRoomByRoomLinkAndUserId(link, id).isPresent();
    }

    @Override
    public void delete(Long id) {
        userRoomRepository.deleteUserRoomByRoomId(id);
        Room room = getSingleResultById(id);
        List<ComponentDTO> components = componentService.getByRoomLink(room.getLink());

        for(ComponentDTO component : components) {
            componentService.delete(component.getId());
        }

        super.delete(id);
    }

    public RoomDTO convertDTO(Room room) {
        RoomDTO roomDTO = modelMapper.map(room, RoomDTO.class);
        roomDTO.setOwner(modelMapper.map(room.getOwner(), UserDTO.class));
        if(room.getParent() != null) {
            roomDTO.setParentId(room.getParent().getId());
        }
        roomDTO.setType("ROOM");
        roomDTO.setUserCreated(modelMapper.map(room.getUserCreated(), UserDTO.class));
        return roomDTO;
    }
}
