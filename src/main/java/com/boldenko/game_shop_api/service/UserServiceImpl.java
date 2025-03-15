package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.UserDto;
import com.boldenko.game_shop_api.entity.User;
import com.boldenko.game_shop_api.mapper.DataMapper;
import com.boldenko.game_shop_api.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final DataMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> {
                   log.info("User not found: " + username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
        log.info("User found: " + user.getUsername());
        return user;
    }

    @Override
    @Transactional
    public UUID createUser(UserDto userDto) {
        User user = mapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        log.info("Add User: " + user.getUsername());
        return user.getId();
    }

    @Override
    @Transactional
    public UUID createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        log.info("Add User: " + user.getUsername());
        return user.getId();
    }

    @Override
    public void deleteUserById(UUID id) {
        String name = mapper.toUserDto(userRepo.findById(id).orElseThrow()).getUsername();
        userRepo.deleteById(id);
        log.info("Delete User: " + name);
    }

    @Override
    public List<UserDto> getAllUser() {
        return List.of((UserDto) userRepo.findAll());
    }
}
