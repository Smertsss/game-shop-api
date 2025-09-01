package com.boldenko.game_shop_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private UUID id;
    private String name;
    private String context;
    private Float cost;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    private Set<String> images;

    private Set<GenreDto> genres;
    private Set<UserDto> likedByUsers;
    private Set<UserDto> dislikedByUsers;
}
