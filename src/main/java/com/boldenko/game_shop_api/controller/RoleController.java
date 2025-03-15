package com.boldenko.game_shop_api.controller;

import com.boldenko.game_shop_api.dto.RoleDto;
import com.boldenko.game_shop_api.entity.Role;
import com.boldenko.game_shop_api.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoleController.PATH_NAME)
@RequiredArgsConstructor
public class RoleController {
    public static final String PATH_NAME = "/api/roles";
    private final RoleServiceImpl roleService;

    @PostMapping
    public UUID createRole(RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @GetMapping("/{id}")
    public RoleDto getRoleById(UUID id) {
        return roleService.getRoleById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRoleById(UUID id) {
        roleService.deleteRoleById(id);
    }

    @GetMapping
    public List<RoleDto> getAllRole() {
        return roleService.getAllRole();
    }
}
