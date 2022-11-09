package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.UserRoomRepository;
import com.tcn.meetandnote.services.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoomService extends BaseService<UserRoom, Long> {

    private final UserRoomRepository userRoomRepository;

    public UserRoomService(UserRoomRepository userRoomRepository) {
        super(userRoomRepository, "User room");
        this.userRoomRepository = userRoomRepository;
    }


    public UserRoom getUserRoomByUserIDAndRoomID(long userId, long roomId) {
        return userRoomRepository.findUserRoomByUserIDAndRoomID(userId, roomId)
                .orElseGet(() -> null);
    }

    public UserRoom getUserRoomOwnerByRoomId(long roomId) {
        return userRoomRepository.findUserRoomOwnerByRoomId(roomId)
                .orElseThrow(() -> new NotFoundException("Not found user-room"));
    }

    public List<Room> getRoomsByUserId(long userId) {
        return userRoomRepository.findRoomsByUserId(userId);
    }

    public List<UserRoom> getUsersByRoomId(long roomId) {
        return userRoomRepository.findUsersByRoomId(roomId);
    }

    @Override
    public UserRoom update(Long id, UserRoom userRoom) {
        UserRoom inDb = getSingleResultById(id);
        inDb.setFullPermission(userRoom.isFullPermission());
        inDb.setRead(userRoom.isRead());
        return userRoomRepository.save(inDb);
    }

}
