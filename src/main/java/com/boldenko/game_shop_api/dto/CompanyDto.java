package com.boldenko.game_shop_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private UUID id;
    private String name;
    private String context;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
}
