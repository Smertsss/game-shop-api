package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.RoleDto;
import com.boldenko.game_shop_api.entity.Role;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final DataMapper mapper;
    private final RoleRepo roleRepo;

    @Override
    @Transactional
    public UUID createRole(RoleDto roleDto) {
        Role role = mapper.toRole(roleDto);
        roleRepo.save(role);
        log.info("Add Role: " + role.getName());
        return role.getId();
    }

    @Override
    @Transactional
    public UUID createRole(Role role) {
        roleRepo.save(role);
        log.info("Add Role: " + role.getName());
        return role.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto getRoleById(UUID id) {
        return mapper.toRoleDto(roleRepo.findById(id).orElseThrow());
    }

    @Override
    public void deleteRoleById(UUID id) {
        String name = mapper.toRoleDto(roleRepo.findById(id).orElseThrow()).getName();
        roleRepo.deleteById(id);
        log.info("Delete Role: " + name);
    }

    @Override
    public List<RoleDto> getAllRole() {
        return List.of((RoleDto) roleRepo.findAll());
    }
}
