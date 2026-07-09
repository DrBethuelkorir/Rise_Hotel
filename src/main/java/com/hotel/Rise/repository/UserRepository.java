package com.hotel.Rise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotel.Rise.models.User;
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    User findByEmail(String email);
}
