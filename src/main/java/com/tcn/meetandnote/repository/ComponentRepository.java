package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ComponentRepository extends JpaRepository<Component, Long> {

    @Query("SELECT c FROM Component c WHERE c.room.link = ?1")
    List<Component> findComponentByRoomLink(String link);

    @Query("SELECT c FROM Component c WHERE c.room.link = ?1 AND c.isDeleted = false")
    List<Component> findComponentByRoomLinkNotTrash(String link);

    @Query("SELECT c FROM Component c WHERE c.room.link = ?1 AND c.isDeleted = false")
    List<Component> findComponentByRoomLinkTrash(String link);

    @Query(value = "SELECT * FROM Components ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Component> findLastComponent();

    @Modifying
    @Query("DELETE FROM Component c WHERE c.room.id = ?1")
    void deleteByRoomId(long id);


}
