package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.attribute.id = ?1")
    List<Todo> findByAttributeId(long id);

    @Query("DELETE FROM Todo t WHERE t.attribute.component.room.id = ?1")
    @Modifying
    void deleteByRoomId(long id);
}
