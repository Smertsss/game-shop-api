package com.boldenko.game_shop_api.repository;

import com.boldenko.game_shop_api.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepo extends JpaRepository<Genre, UUID> {
}
