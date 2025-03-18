package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GenreDto;
import com.boldenko.game_shop_api.entity.Genre;

import java.util.List;
import java.util.UUID;

public interface GenreService {
    UUID createGenre(GenreDto genreDto);
    UUID createGenre(Genre genre);
    GenreDto getGenreById(UUID id);
    void deleteGenreById(UUID id);
    List<GenreDto> getAllGenre();
}
