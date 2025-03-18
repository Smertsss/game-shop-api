package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.RoleDto;
import com.boldenko.game_shop_api.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    UUID createRole(RoleDto roleDto);
    UUID createRole(Role role);
    RoleDto getRoleById(UUID id);
    void deleteRoleById(UUID id);
    List<RoleDto> getAllRole();
}
