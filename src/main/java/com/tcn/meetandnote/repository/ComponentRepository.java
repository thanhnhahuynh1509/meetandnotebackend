package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}
