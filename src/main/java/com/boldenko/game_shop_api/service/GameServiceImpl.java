package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.entity.Game;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.GameRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{
    private final DataMapper mapper;
    private final GameRepo gameRepo;

    @Override
    @Transactional
    public UUID createGame(GameDto gameDto) {
        Game game = gameRepo.save(mapper.toGame(gameDto));
        log.info("Add Game: " + game.getName());
        return game.getId();
    }

    @Override
    @Transactional
    public UUID createGame(Game game) {
        UUID id = gameRepo.save(game).getId();
        log.info("Add Game: " + game.getName());
        return id;
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
        List<Game> games = gameRepo.findAll();
        log.info("Found {} games in database", games.size());

        List<GameDto> result = new ArrayList<>();

        for (Game game : games) {
            log.info("Processing game: ID={}, Name={}, CreationDate={}",
                    game.getId(), game.getName(), game.getCreationDate());

            GameDto dto = new GameDto();
            dto.setId(game.getId());
            dto.setName(game.getName());
            dto.setContext(game.getContext());
            dto.setCost(game.getCost());
            dto.setCreationDate(game.getCreationDate());
            dto.setUpdateDate(game.getUpdateDate());

            log.info("Created DTO: {}", dto);
            result.add(dto);
        }

        log.info("Returning {} game DTOs", result.size());
        return result;
    }
}
