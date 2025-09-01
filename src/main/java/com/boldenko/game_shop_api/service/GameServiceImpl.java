package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.dto.GenreDto;
import com.boldenko.game_shop_api.entity.Game;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.GameRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final DataMapper mapper;
    private final GameRepo gameRepo;
    private final ImageStorageService imageStorageService;

    @Override
    @Transactional
    public UUID createGame(GameDto gameDto) {
        Game game = mapper.toGame(gameDto);
        game.setCreationDate(LocalDate.now());
        game.setUpdateDate(LocalDate.now());

        Game savedGame = gameRepo.save(game);
        log.info("Add Game: " + game.getName());
        return savedGame.getId();
    }

    @Override
    @Transactional
    public UUID createGame(Game game) {
        game.setCreationDate(LocalDate.now());
        game.setUpdateDate(LocalDate.now());

        UUID id = gameRepo.save(game).getId();
        log.info("Add Game: " + game.getName());
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public GameDto getGameById(UUID id) {
        Game game = gameRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Game not found with id: {}", id);
                    return new RuntimeException("Game not found with id: " + id);
                });

        log.info("Found game: ID={}, Name={}, Images={}",
                game.getId(), game.getName(), game.getImages());

        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setContext(game.getContext());
        gameDto.setCost(game.getCost());
        gameDto.setCreationDate(game.getCreationDate());
        gameDto.setUpdateDate(game.getUpdateDate());
        gameDto.setImages(game.getImages());

        // Если нужно маппить связанные сущности
        if (game.getGenres() != null) {
            gameDto.setGenres(game.getGenres().stream()
                    .map(genre -> {
                        GenreDto genreDto = new GenreDto();
                        genreDto.setId(genre.getId());
                        genreDto.setName(genre.getName());
                        return genreDto;
                    })
                    .collect(Collectors.toSet()));
        }

        log.info("Mapped to DTO: ID={}, Name={}, Images={}",
                gameDto.getId(), gameDto.getName(), gameDto.getImages());

        return gameDto;
    }

    @Override
    @Transactional
    public void deleteGameById(UUID id) {
        Game game = gameRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));

        // Удаляем связанные изображения
        if (game.getImages() != null) {
            for (String imageName : game.getImages()) {
                try {
                    imageStorageService.deleteImage("Game", imageName);
                } catch (IOException e) {
                    log.warn("Failed to delete image: {}", imageName, e);
                }
            }
        }

        String gameName = game.getName();
        gameRepo.deleteById(id);
        log.info("Delete Game: " + gameName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameDto> getAllGame() {
        List<Game> games = gameRepo.findAll();
        log.info("Found {} games in database", games.size());

        return games.stream()
                .map(game -> {
                    GameDto dto = new GameDto();
                    dto.setId(game.getId());
                    dto.setName(game.getName());
                    dto.setContext(game.getContext());
                    dto.setCost(game.getCost());
                    dto.setCreationDate(game.getCreationDate());
                    dto.setUpdateDate(game.getUpdateDate());
                    dto.setImages(game.getImages());

                    log.info("Created DTO for game: ID={}, Name={}", game.getId(), game.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllGameData() {
        List<Game> games = gameRepo.findAll();
        log.info("Found {} games in database for data table", games.size());

        return games.stream()
                .map(game -> {
                    Map<String, Object> gameData = new LinkedHashMap<>();

                    gameData.put("id", game.getId().toString());
                    gameData.put("name", game.getName());
                    gameData.put("context", game.getContext());
                    gameData.put("cost", game.getCost());
                    gameData.put("creationDate", game.getCreationDate());
                    gameData.put("updateDate", game.getUpdateDate());

                    gameData.put("users", game.getUsers() != null ? game.getUsers().size() : 0);
                    gameData.put("likedByUsers", game.getLikedByUsers() != null ? game.getLikedByUsers().size() : 0);
                    gameData.put("dislikedByUsers", game.getDislikedByUsers() != null ? game.getDislikedByUsers().size() : 0);
                    gameData.put("companies", game.getCompanies() != null ? game.getCompanies().size() : 0);
                    gameData.put("genres", game.getGenres() != null ? game.getGenres().size() : 0);
                    gameData.put("images", game.getImages() != null ? game.getImages().size() : 0);

                    log.info("Created data for game: {}, users: {}, liked: {}, disliked: {}, companies: {}, genres: {}, images: {}",
                            game.getName(),
                            game.getUsers() != null ? game.getUsers().size() : 0,
                            game.getLikedByUsers() != null ? game.getLikedByUsers().size() : 0,
                            game.getDislikedByUsers() != null ? game.getDislikedByUsers().size() : 0,
                            game.getCompanies() != null ? game.getCompanies().size() : 0,
                            game.getGenres() != null ? game.getGenres().size() : 0,
                            game.getImages() != null ? game.getImages().size() : 0);

                    return gameData;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GameDto updateGame(UUID id, GameDto gameDto) {
        Game existingGame = gameRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));

        if (gameDto.getName() != null) {
            existingGame.setName(gameDto.getName());
        }
        if (gameDto.getContext() != null) {
            existingGame.setContext(gameDto.getContext());
        }
        if (gameDto.getCost() != null) {
            existingGame.setCost(gameDto.getCost());
        }
        existingGame.setUpdateDate(LocalDate.now());

        Game updatedGame = gameRepo.save(existingGame);
        log.info("Updated game: {}", updatedGame.getName());

        return mapper.toGameDto(updatedGame);
    }

    @Override
    @Transactional
    public String addImageToGame(UUID gameId, MultipartFile file) {
        Game game = gameRepo.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        try {
            log.info("Adding image to game: {}", game.getName());
            log.info("File details: name={}, size={} bytes", file.getOriginalFilename(), file.getSize());

            MultipartFile[] files = {file};
            Set<String> savedFileNames = imageStorageService.saveImages(files, "Game");

            if (savedFileNames.isEmpty()) {
                log.error("No files were saved");
                throw new RuntimeException("Failed to save image");
            }

            String fileName = savedFileNames.iterator().next();
            log.info("Image saved with name: {}", fileName);

            if (game.getImages() == null) {
                game.setImages(new HashSet<>());
                log.info("Initialized images collection for game");
            }

            game.getImages().add(fileName);
            gameRepo.save(game);

            log.info("Added image to game: {}, total images: {}", game.getName(), game.getImages().size());
            return fileName;

        } catch (IOException e) {
            log.error("Failed to store image for game: {}", gameId, e);
            throw new RuntimeException("Failed to store image for game: " + gameId, e);
        }
    }

    @Override
    @Transactional
    public void removeImageFromGame(UUID gameId, String fileName) {
        Game game = gameRepo.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        if (game.getImages() != null && game.getImages().contains(fileName)) {
            try {
                imageStorageService.deleteImage("Game", fileName);

                game.getImages().remove(fileName);
                gameRepo.save(game);

                log.info("Removed image from game: {}", game.getName());

            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image: " + fileName, e);
            }
        }
    }

    @Override
    @Transactional
    public Set<String> addMultipleImagesToGame(UUID gameId, MultipartFile[] files) {
        Game game = gameRepo.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        try {
            Set<String> savedFileNames = imageStorageService.saveImages(files, "Game");

            if (game.getImages() == null) {
                game.setImages(new HashSet<>());
            }

            game.getImages().addAll(savedFileNames);
            gameRepo.save(game);

            log.info("Added {} images to game: {}", savedFileNames.size(), game.getName());
            return savedFileNames;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store images for game: " + gameId, e);
        }
    }

    public List<GameDto> getFreeGames() {
        List<Game> freeGames = gameRepo.findByCost(0f);
        log.info("Found {} free games in database", freeGames.size());

        return freeGames.stream()
                .map(game -> {
                    GameDto dto = new GameDto();
                    dto.setId(game.getId());
                    dto.setName(game.getName());
                    dto.setContext(game.getContext());
                    dto.setCost(game.getCost());
                    dto.setCreationDate(game.getCreationDate());
                    dto.setUpdateDate(game.getUpdateDate());
                    dto.setImages(game.getImages());

                    log.info("Created DTO for free game: ID={}, Name={}", game.getId(), game.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<GameDto> getTopGames() {
        List<Game> allGames = gameRepo.findAll();
        log.info("Found {} games for top ranking", allGames.size());

        return allGames.stream()
                .sorted((g1, g2) -> Integer.compare(
                        g2.getLikedByUsers() != null ? g2.getLikedByUsers().size() : 0,
                        g1.getLikedByUsers() != null ? g1.getLikedByUsers().size() : 0
                ))
                .map(game -> {
                    GameDto dto = new GameDto();
                    dto.setId(game.getId());
                    dto.setName(game.getName());
                    dto.setContext(game.getContext());
                    dto.setCost(game.getCost());
                    dto.setCreationDate(game.getCreationDate());
                    dto.setUpdateDate(game.getUpdateDate());
                    dto.setImages(game.getImages());

                    log.info("Created DTO for top game: ID={}, Name={}, Likes={}",
                            game.getId(), game.getName(),
                            game.getLikedByUsers() != null ? game.getLikedByUsers().size() : 0);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}