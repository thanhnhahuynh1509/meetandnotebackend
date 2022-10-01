package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

    @Query("SELECT ur FROM UserRoom ur WHERE ur.user.id = ?1 AND ur.room.id = ?2")
    Optional<UserRoom> findUserRoomByUserIDAndRoomID(long userId, long roomId);

}
