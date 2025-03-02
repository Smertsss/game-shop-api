package com.boldenko.game_shop_api.repository;

import com.boldenko.game_shop_api.entity.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GameRepo extends CrudRepository<Game, UUID> {
}
