package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.CompanyDto;
import com.boldenko.game_shop_api.dto.GenreDto;
import com.boldenko.game_shop_api.entity.Company;
import com.boldenko.game_shop_api.entity.Genre;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.GenreRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
    private final DataMapper mapper;
    private final GenreRepo genreRepo;

    @Override
    @Transactional
    public UUID createGenre(GenreDto genreDto) {
        Genre genre = genreRepo.save(mapper.toGenre(genreDto));
        log.info("Add Genre: " + genre.getName());
        return genre.getId();
    }

    @Override
    @Transactional
    public UUID createGenre(Genre genre) {
        UUID id = genreRepo.save(genre).getId();
        log.info("Add Genre: " + genre.getName());
        return id;
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
        List<Genre> genres = genreRepo.findAll();
        log.info("Found {} genres in database", genres.size());

        return genres.stream()
                .map(genre -> {
                    GenreDto dto = new GenreDto();
                    dto.setId(genre.getId());
                    dto.setName(genre.getName());

                    log.info("Created DTO for genre: ID={}, Name={}", genre.getId(), genre.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllGenreData() {
        List<Genre> genres = genreRepo.findAll();
        log.info("Found {} genres in database for data table", genres.size());

        return genres.stream()
                .map(genre -> {
                    Map<String, Object> genreData = new LinkedHashMap<>();

                    genreData.put("id", genre.getId().toString());
                    genreData.put("name", genre.getName());

                    genreData.put("games", genre.getGames() != null ? genre.getGames().size() : 0);

                    log.info("Created data for genre: {}, games: {}",
                            genre.getName(),
                            genre.getGames() != null ? genre.getGames().size() : 0);
                    return genreData;
                })
                .collect(Collectors.toList());
    }
}
