package com.boldenko.game_shop_api.repository;

import com.boldenko.game_shop_api.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepo extends JpaRepository<Game, UUID> {
    List<Game> findByCost(float cost);
}
