package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

    @Query("SELECT ur FROM UserRoom ur WHERE ur.user.id = ?1 AND ur.room.id = ?2")
    Optional<UserRoom> findUserRoomByUserIDAndRoomID(long userId, long roomId);

    @Query("SELECT ur FROM UserRoom ur WHERE ur.room.link = ?1 AND ur.user.id = ?2")
    Optional<UserRoom> findRoomByRoomLinkAndUserId(String roomLink, long userId);

    @Query("SELECT ur FROM UserRoom ur WHERE ur.room.id = ?1 AND ur.isOwner=true")
    Optional<UserRoom> findUserRoomOwnerByRoomId(long roomId);

    @Query("SELECT ur.room FROM UserRoom ur WHERE ur.room.link = ?1 AND ur.user.id = ?2 AND ur.isOwner=true")
    Optional<Room> findRoomByOwnerAndRoomLinkAndUserId(String roomLink, long userId);

    @Query("SELECT ur.room FROM UserRoom ur WHERE ur.user.id = ?1")
    List<Room> findRoomsByUserId(long userId);

    @Query("SELECT ur.user FROM UserRoom ur WHERE ur.room.id = ?1")
    List<User> findUsersByRoomId(long roomId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserRoom ur WHERE ur.room.id = ?1")
    void deleteUserRoomByRoomId(long roomId);
}
