package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.GenreDto;
import com.boldenko.game_shop_api.entity.Genre;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.GenreRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
    private final DataMapper mapper;
    private final GenreRepo genreRepo;

    @Override
    @Transactional
    public UUID createGenre(GenreDto genreDto) {
        Genre genre = mapper.toGenre(genreDto);
        genreRepo.save(genre);
        log.info("Add Genre: " + genre.getName());
        return genre.getId();
    }

    @Override
    @Transactional
    public UUID createGenre(Genre genre) {
        genreRepo.save(genre);
        log.info("Add Genre: " + genre.getName());
        return genre.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto getGenreById(UUID id) {
        return mapper.toGenreDto(genreRepo.findById(id).orElseThrow());
    }

    @Override
    public void deleteGenreById(UUID id) {
        String name = mapper.toGenreDto(genreRepo.findById(id).orElseThrow()).getName();
        genreRepo.deleteById(id);
        log.info("Delete Genre: " + name);
    }

    @Override
    public List<GenreDto> getAllGenre() {
        return List.of((GenreDto) genreRepo.findAll());
    }
}
