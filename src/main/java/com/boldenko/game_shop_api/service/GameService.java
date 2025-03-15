package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.entity.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {
    UUID createGame(GameDto gameDto);
    UUID createGame(Game game);
    GameDto getGameById(UUID id);
    void deleteGameById(UUID id);
    List<GameDto> getAllGame();
}
