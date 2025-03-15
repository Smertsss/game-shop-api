package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.CompanyDto;
import com.boldenko.game_shop_api.dto.UserDto;
import com.boldenko.game_shop_api.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UUID createUser(UserDto userDto);
    UUID createUser(User user);
    void deleteUserById(UUID id);
    List<UserDto> getAllUser();
}
