package com.boldenko.game_shop_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private UUID id;
    private String name;
    private String context;
    private float cost;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;
}
