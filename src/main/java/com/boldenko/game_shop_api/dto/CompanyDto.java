package com.boldenko.game_shop_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyDto {
    private UUID id;
    private String name;
    private String context;
    private LocalDateTime creationDate;
}
