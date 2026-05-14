package com.b1uffer.multisessiontest.repository;

import com.b1uffer.multisessiontest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
