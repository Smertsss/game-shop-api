package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.entity.Game;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GameService {
    UUID createGame(GameDto gameDto);
    UUID createGame(Game game);
    GameDto getGameById(UUID id);
    void deleteGameById(UUID id);
    List<GameDto> getAllGame();
    GameDto updateGame(UUID id, GameDto gameDto);
    String addImageToGame(UUID gameId, MultipartFile file);
    void removeImageFromGame(UUID gameId, String fileName);
    Set<String> addMultipleImagesToGame(UUID gameId, MultipartFile[] files);
}
