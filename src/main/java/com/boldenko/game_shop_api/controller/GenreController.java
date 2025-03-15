package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.GenreDto;
import com.boldenko.game_shop_api.entity.Genre;
import com.boldenko.game_shop_api.service.GenreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(GenreController.PATH_NAME)
@RequiredArgsConstructor
public class GenreController {
    public static final String PATH_NAME = "/api/genres";
    private final GenreServiceImpl genreService;

    @PostMapping
    public UUID createGenre(GenreDto genreDto) {
        return genreService.createGenre(genreDto);
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(UUID id) {
        return genreService.getGenreById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGenreById(UUID id) {
        genreService.deleteGenreById(id);
    }

    @GetMapping
    public List<GenreDto> getAllGenre() {
        return genreService.getAllGenre();
    }
}
