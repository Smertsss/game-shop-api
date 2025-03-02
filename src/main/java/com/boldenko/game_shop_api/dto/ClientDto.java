package com.boldenko.game_shop_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private UUID id;
    private String firstName;
    private String secondName;
    private String username;
    private String email;
    private String login;
    private String password;
    private LocalDateTime creationDate;
    private LocalDateTime lastLoginDate;
    private boolean online;
    private boolean moderator;

}
