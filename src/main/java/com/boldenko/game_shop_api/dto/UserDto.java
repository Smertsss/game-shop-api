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
