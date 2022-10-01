package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.UserRoomRepository;
import com.tcn.meetandnote.services.UserRoomService;
import org.springframework.stereotype.Service;

@Service
public class UserRoomServiceImpl implements UserRoomService {

    private final UserRoomRepository userRoomRepository;

    public UserRoomServiceImpl(UserRoomRepository userRoomRepository) {
        this.userRoomRepository = userRoomRepository;
    }

    @Override
    public UserRoom save(UserRoom userRoom) {
        return userRoomRepository.save(userRoom);
    }

    @Override
    public UserRoom getUserRoomByUserIDAndRoomID(long userId, long roomId) {
        return userRoomRepository.findUserRoomByUserIDAndRoomID(userId, roomId)
                .orElseThrow(() -> new NotFoundException("Not found user-room"));
    }

    @Override
    public UserRoom update(long id, UserRoom userRoom) {
        UserRoom inDb = getUserRoomById(id);
        inDb.setFullPermission(userRoom.isFullPermission());
        inDb.setRead(userRoom.isRead());
        return userRoomRepository.save(inDb);
    }

    @Override
    public void delete(long id) {
        userRoomRepository.delete(getUserRoomById(id));
    }

    private UserRoom getUserRoomById(long id) {
        return userRoomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found and room with id: " + id));
    }
}
