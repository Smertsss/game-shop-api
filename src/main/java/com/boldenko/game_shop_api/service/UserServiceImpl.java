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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final DataMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepo.findByIdentifier(identifier)
                .orElseThrow(() -> {
                    log.info("User not found: " + identifier);
                    return new UsernameNotFoundException("User not found with identifier: " + identifier);
                });
        log.info("User found: " + user.getUsername());
        return user;
    }

    @Override
    @Transactional
    public UUID createUser(UserDto userDto) {
        Optional<User> existingUserByEmail = userRepo.findByEmail(userDto.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        Optional<User> existingUserByLogin = userRepo.findByLogin(userDto.getLogin());
        if (existingUserByLogin.isPresent()) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        Optional<User> existingUserByUsername = userRepo.findByUsername(userDto.getUsername());
        if (existingUserByUsername.isPresent()) {
            throw new RuntimeException("Пользователь с таким именем пользователя уже существует");
        }

        User user = mapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreationDate(LocalDate.now());
        user.setLastLoginDate(LocalDate.now());
        user.setOnline(true);

        UUID id = userRepo.save(user).getId();
        log.info("Add User: " + user.getUsername());
        return id;
    }

    @Override
    @Transactional
    public UUID createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreationDate(LocalDate.now());
        user.setLastLoginDate(LocalDate.now());
        user.setOnline(true);

        UUID id = userRepo.save(user).getId();
        log.info("Add User: " + user.getUsername());
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(UUID id) {
        return mapper.toUserDto(userRepo.findById(id).orElseThrow());
    }

    @Override
    public void deleteUserById(UUID id) {
        String name = mapper.toUserDto(userRepo.findById(id).orElseThrow()).getUsername();
        userRepo.deleteById(id);
        log.info("Delete User: " + name);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepo.findAll();
        log.info("Found {} users in database", users.size());

        List<UserDto> result = new ArrayList<>();

        for (User user : users) {
            log.info("Processing game: ID={}, Username={}, CreationDate={}",
                    user.getId(), user.getUsername(), user.getCreationDate());

            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setSecondName(user.getSecondName());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setLogin(user.getLogin());
            dto.setPassword(user.getPassword());
            dto.setCreationDate(user.getCreationDate());
            dto.setLastLoginDate(user.getLastLoginDate());
            dto.setOnline(user.isOnline());

            log.info("Created DTO: {}", dto);
            result.add(dto);
        }

        log.info("Returning {} user DTOs", result.size());
        return result;
    }
}
