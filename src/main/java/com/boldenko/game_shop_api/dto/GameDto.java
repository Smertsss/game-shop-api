package com.boldenko.game_shop_api.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private UUID id;
    private String name;
    private String context;
    private float cost;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
