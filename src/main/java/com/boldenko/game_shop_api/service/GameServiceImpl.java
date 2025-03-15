package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.entity.Game;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.GameRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{
    private final DataMapper mapper;
    private final GameRepo gameRepo;

    @Override
    @Transactional
    public UUID createGame(GameDto gameDto) {
        Game game = mapper.toGame(gameDto);
        gameRepo.save(game);
        log.info("Add Game: " + game.getName());
        return game.getId();
    }

    @Override
    @Transactional
    public UUID createGame(Game game) {
        gameRepo.save(game);
        log.info("Add Game: " + game.getName());
        return game.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public GameDto getGameById(UUID id) {
        return mapper.toGameDto(gameRepo.findById(id).orElseThrow());
    }

    @Override
    public void deleteGameById(UUID id) {
        String name = mapper.toGameDto(gameRepo.findById(id).orElseThrow()).getName();
        gameRepo.deleteById(id);
        log.info("Delete Game: " + name);
    }

    @Override
    public List<GameDto> getAllGame() {
        return List.of((GameDto) gameRepo.findAll());
    }
}
