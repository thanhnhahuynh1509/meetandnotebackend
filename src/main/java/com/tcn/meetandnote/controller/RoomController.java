package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.dto.RoomDTO;
import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.services.impl.RoomService;
import com.tcn.meetandnote.services.impl.UserRoomService;
import com.tcn.meetandnote.services.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final UserService userService;
    private final UserRoomService userRoomService;
    private final ModelMapper modelMapper;

    public RoomController(RoomService roomService, UserService userService, UserRoomService userRoomService, ModelMapper modelMapper) {
        this.roomService = roomService;
        this.userService = userService;
        this.userRoomService = userRoomService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/link/{link}")
    public RoomDTO getRoomByLink(@PathVariable String link) {
        Room room = roomService.getByLink(link);
        RoomDTO roomDTO = modelMapper.map(room, RoomDTO.class);
        if(room.getParent() != null) {
            roomDTO.setParentId(room.getParent().getId());
        }
        return roomDTO;
    }

    @GetMapping("/children/{link}")
    public List<RoomDTO> getChildrenRoomByLink(@PathVariable String link) {
        Room room = roomService.getByLink(link);
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for(Room r : room.getChildren()) {
            RoomDTO roomDTO = modelMapper.map(r, RoomDTO.class);
            roomDTO.setType("ROOM");
            UserRoom userRoom = userRoomService.getUserRoomOwnerByRoomId(r.getId());
            UserDTO userDTO = modelMapper.map(userRoom.getUser(), UserDTO.class);
            roomDTO.setUser(userDTO);
            roomDTOS.add(roomDTO);
        }
        return roomDTOS;
    }

    @GetMapping("/last-id")
    public long getLastID() {
        long id = roomService.getLastID();
        return id;
    }

    @GetMapping("/check-user-in-room/{roomLink}/users/{userId}")
    public boolean checkUserInRoom(@PathVariable String roomLink, @PathVariable long userId) {
        return roomService.checkUserInRoom(roomLink, userId);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> save(@RequestBody RoomDTO roomDTO) {
        UserDTO userDTO = roomDTO.getUser();
        Room room = new Room();
        room.setPosX(roomDTO.getPosX());
        room.setPosY(roomDTO.getPosY());
        room.setParent(new Room(roomDTO.getParentId()));
        room.setTitle("New Room");
        room = roomService.save(room);

        userService.addRoom(room.getId(), roomDTO.getUser().getId());

        roomDTO = modelMapper.map(room, RoomDTO.class);
        roomDTO.setParentId(room.getParent().getId());
        roomDTO.setType("ROOM");
        roomDTO.setUser(userDTO);
        return new ResponseEntity<>(roomDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{roomId}/users/{email}/{permission}")
    public String inviteUserRoom(@PathVariable long roomId, @PathVariable String email, @PathVariable String permission) {
        return roomService.inviteUser(roomId, email, permission);
    }

    @GetMapping("/link/{link}/users/{userId}")
    public RoomDTO getRoomOwnerByLinkAndUser(@PathVariable String link, @PathVariable long userId) {
        return roomService.getRoomOwnerByUserAndLink(link, userId);
    }

    @GetMapping("/users/{userId}")
    public List<RoomDTO> getRoomsByUserId(@PathVariable long userId) {
        return roomService.getRoomByUserId(userId);
    }

    @PutMapping("/position/{id}")
    public RoomDTO updatePosition(@PathVariable long id, @RequestBody RoomDTO roomDTO) {
        return modelMapper.map(roomService.updatePosition(id, roomDTO), RoomDTO.class);
    }

    @PutMapping("/information/{id}")
    public RoomDTO updateInformation(@PathVariable long id, @RequestBody RoomDTO roomDTO) {
        return modelMapper.map(roomService.updateInformation(id, roomDTO), RoomDTO.class);
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable long id) {
        roomService.delete(id);
        return "OK";
    }

}
