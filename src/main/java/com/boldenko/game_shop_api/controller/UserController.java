package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.UserDto;
import com.boldenko.game_shop_api.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UserController.PATH_NAME)
@RequiredArgsConstructor
public class UserController {
    public static final String PATH_NAME = "/api/clients";
    private final UserServiceImpl userService;

    @PostMapping
    public UUID createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
    }

    @GetMapping
    public List<UserDto> getAllUser() {
        return userService.getAllUser();
    }
}
