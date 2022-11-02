package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Long> {

    @Query("SELECT c FROM Component c WHERE c.room.link = ?1")
    List<Component> findComponentByRoomLink(String link);

    @Query("SELECT c FROM Component c WHERE c.room.link = ?1 AND c.isDeleted = false")
    List<Component> findComponentByRoomLinkNotTrash(String link);

    @Query("SELECT c FROM Component c WHERE c.room.link = ?1 AND c.isDeleted = false")
    List<Component> findComponentByRoomLinkTrash(String link);
}
