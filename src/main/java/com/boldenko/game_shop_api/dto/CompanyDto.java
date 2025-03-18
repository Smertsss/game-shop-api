package com.boldenko.game_shop_api.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private UUID id;
    private String name;
    private String context;
    private LocalDateTime creationDate;
}
