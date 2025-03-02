package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.entity.Game;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private DataMapper mapper;

    @Override
    @Transactional
    public UUID createGame(GameDto gameDto) {
        Game game = mapper.toGame(gameDto);
        gameRepo.save(game);
        return game.getId();
    }

    @Override
    @Transactional
    public UUID createGame(Game game) {
        gameRepo.save(game);
        return game.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public GameDto getGameById(UUID id) {
        return mapper.toGameDto(gameRepo.findById(id).orElseThrow());
    }
}
