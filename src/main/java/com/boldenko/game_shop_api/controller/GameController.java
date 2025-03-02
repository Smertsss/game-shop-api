package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(GameController.PATH)
public class GameController {
    public static final String PATH = "/api/games";

    @Autowired
    private GameService gameService;

    @GetMapping
    GameDto getGame(@RequestParam UUID id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    UUID createGame(@RequestBody GameDto gameDto) {
        return gameService.createGame(gameDto);
    }
}
