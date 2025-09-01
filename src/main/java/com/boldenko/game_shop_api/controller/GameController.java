package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.GameDto;
import com.boldenko.game_shop_api.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(GameController.PATH_NAME)
public class GameController {
    public static final String PATH_NAME = "/api/games";
    private final GameServiceImpl gameService;

    @PostMapping
    public UUID createGame(@RequestBody GameDto gameDto) {
        return gameService.createGame(gameDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable UUID id) {
        log.info("Request for game with ID: {}", id);
        try {
            GameDto gameDto = gameService.getGameById(id);
            log.info("Returning game: ID={}, Name={}, Images count={}",
                    gameDto.getId(), gameDto.getName(),
                    gameDto.getImages() != null ? gameDto.getImages().size() : 0);
            return ResponseEntity.ok(gameDto);
        } catch (RuntimeException e) {
            log.error("Game not found with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteGameById(@PathVariable UUID id) {
        gameService.deleteGameById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameDto> getAllGame() {
        return gameService.getAllGame();
    }

    @GetMapping("/data")
    public List<Map<String, Object>> getAllGameData() {
        return gameService.getAllGameData();
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<String> uploadGameImage(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {

        log.info("Received image upload request for game ID: {}", id);
        log.info("File details: name={}, size={}, content-type={}",
                file.getOriginalFilename(), file.getSize(), file.getContentType());

        try {
            String fileName = gameService.addImageToGame(id, file);
            log.info("Image uploaded successfully: {}", fileName);
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            log.error("Failed to upload image for game: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/images/multiple")
    public ResponseEntity<Set<String>> uploadMultipleGameImages(
            @PathVariable UUID id,
            @RequestParam("files") MultipartFile[] files) {

        Set<String> fileNames = gameService.addMultipleImagesToGame(id, files);
        return ResponseEntity.ok(fileNames);
    }

    @DeleteMapping("/{id}/images/{fileName}")
    public ResponseEntity<Void> deleteGameImage(
            @PathVariable UUID id,
            @PathVariable String fileName) {

        gameService.removeImageFromGame(id, fileName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(
            @PathVariable UUID id,
            @RequestBody GameDto gameDto) {

        GameDto updatedGame = gameService.updateGame(id, gameDto);
        return ResponseEntity.ok(updatedGame);
    }

    @GetMapping("/free")
    public List<GameDto> getFreeGames() {
        return gameService.getFreeGames();
    }

    @GetMapping("/top")
    public List<GameDto> getTopGames() {
        return gameService.getTopGames();
    }
}