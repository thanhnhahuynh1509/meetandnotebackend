package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.UserRoomRepository;
import com.tcn.meetandnote.services.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UserRoomService extends BaseService<UserRoom, Long> {

    private final UserRoomRepository userRoomRepository;

    public UserRoomService(UserRoomRepository userRoomRepository) {
        super(userRoomRepository, "User room");
        this.userRoomRepository = userRoomRepository;
    }



    public UserRoom getUserRoomByUserIDAndRoomID(long userId, long roomId) {
        return userRoomRepository.findUserRoomByUserIDAndRoomID(userId, roomId)
                .orElseThrow(() -> new NotFoundException("Not found user-room"));
    }

    public UserRoom getUserRoomOwnerByRoomId(long roomId) {
        return userRoomRepository.findUserRoomOwnerByRoomId(roomId)
                .orElseThrow(() -> new NotFoundException("Not found user-room"));
    }

    @Override
    public UserRoom update(Long id, UserRoom userRoom) {
        UserRoom inDb = getSingleResultById(id);
        inDb.setFullPermission(userRoom.isFullPermission());
        inDb.setRead(userRoom.isRead());
        return userRoomRepository.save(inDb);
    }

}
