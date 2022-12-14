package com.tcn.meetandnote.repository;

import com.tcn.meetandnote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.verifyToken = ?1")
    Optional<User> findUserByVerifyToken(String token);
}
