package com.tcn.meetandnote.services;

import com.tcn.meetandnote.entity.UserRoom;

public interface UserRoomService {
    UserRoom save(UserRoom userRoom);
    UserRoom getUserRoomByUserIDAndRoomID(long userId, long roomId);
    UserRoom update(long id, UserRoom userRoom);
    void delete(long id);
}
