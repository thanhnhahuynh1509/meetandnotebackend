package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.link = ?1 AND r.fullPermissionToken = ?2")
    Optional<Room> findByLinkAndFullPermissionToken(String link, String permissionToken);

    @Query("SELECT r FROM Room r WHERE r.link = ?1")
    Optional<Room> findByLink(String link);

    @Query(value = "SELECT * FROM Rooms ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Room> findLastRoom();

    @Query("SELECT r FROM Room r WHERE r.owner.id = ?1")
    List<Room> findRoomByOwner(long userId);

}
