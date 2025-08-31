package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.dto.CompanyDto;
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
import java.util.*;
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
    @Transactional(readOnly = true)
    public List<UserDto> getAllUser() {
        List<User> users = userRepo.findAll();
        log.info("Found {} users in database", users.size());

        return users.stream()
                .map(user -> {
                    UserDto dto = new UserDto();

                    dto.setId(user.getId());
                    dto.setFirstName(user.getFirstName());
                    dto.setSecondName(user.getSecondName());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setCreationDate(user.getCreationDate());
                    dto.setLastLoginDate(user.getLastLoginDate());
                    dto.setOnline(user.isOnline());

                    log.info("Created DTO for user: ID={}, Name={}", user.getId(), user.getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllUserData() {
        List<User> users = userRepo.findAll();
        log.info("Found {} users in database for data table", users.size());

        return users.stream()
                .map(user -> {
                    Map<String, Object> userData = new LinkedHashMap<>();

                    userData.put("id", user.getId().toString());
                    userData.put("firstName", user.getFirstName());
                    userData.put("secondName", user.getSecondName());
                    userData.put("username", user.getUsername());
                    userData.put("email", user.getEmail());
                    userData.put("creationDate", user.getCreationDate());
                    userData.put("lastLoginDate", user.getLastLoginDate());
                    userData.put("online", user.isOnline());

                    userData.put("games", user.getGames() != null ? user.getGames().size() : 0);
                    userData.put("likedGames", user.getLikedGames() != null ? user.getLikedGames().size() : 0);
                    userData.put("dislikedGames", user.getDislikedGames() != null ? user.getDislikedGames().size() : 0);
                    userData.put("roles", user.getRoles() != null ? user.getRoles().size() : 0);
                    userData.put("companies", user.getCompanies() != null ? user.getCompanies().size() : 0);
                    userData.put("images", user.getImages() != null ? user.getImages().size() : 0);

                    log.info("Created data for user: {}, games: {}, likedGames: {}, dislikedGames: {}, " +
                                    "roles: {}, companies: {}, images: {}",
                            user.getUsername(),
                            user.getGames() != null ? user.getGames().size() : 0,
                            user.getLikedGames() != null ? user.getLikedGames().size() : 0,
                            user.getDislikedGames() != null ? user.getDislikedGames().size() : 0,
                            user.getRoles() != null ? user.getRoles().size() : 0,
                            user.getCompanies() != null ? user.getCompanies().size() : 0,
                            user.getImages() != null ? user.getImages().size() : 0);
                    return userData;
                })
                .collect(Collectors.toList());
    }
}
