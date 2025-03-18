package com.boldenko.game_shop_api.repository;

import com.boldenko.game_shop_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.login = :identifier")
    Optional<User> findByIdentifier(String identifier);
}
