package com.hotel.Rise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotel.Rise.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
