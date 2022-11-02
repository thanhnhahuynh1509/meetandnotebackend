package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    @Query("SELECT t FROM Type t WHERE t.name = ?1")
    Optional<Type> findTypeByName(String name);

}
