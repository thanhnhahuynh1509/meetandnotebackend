package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Attribute;
import com.tcn.meetandnote.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query("SELECT a FROM Attribute a WHERE a.component.id = ?1")
    Optional<Attribute> findByComponentId(long componentId);
}
