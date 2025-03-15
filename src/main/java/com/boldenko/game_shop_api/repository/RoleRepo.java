package com.boldenko.game_shop_api.repository;

import com.boldenko.game_shop_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
}
