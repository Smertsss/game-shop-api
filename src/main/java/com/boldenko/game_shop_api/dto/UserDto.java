package com.boldenko.game_shop_api.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
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
}
