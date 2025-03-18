package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(GameController.PATH_NAME)
@RequiredArgsConstructor
public class GameController {
    public static final String PATH_NAME = "/api/games";
    private final GameServiceImpl gameService;

    @PostMapping
    public UUID createGame(@RequestBody GameDto gameDto) {
        return gameService.createGame(gameDto);
    }

    @GetMapping("/{id}")
    public GameDto getGameById(@PathVariable UUID id) {
        return gameService.getGameById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGameById(@PathVariable UUID id) {
        gameService.deleteGameById(id);
    }

    @GetMapping
    public List<GameDto> getAllGame() {
        return gameService.getAllGame();
    }
}
