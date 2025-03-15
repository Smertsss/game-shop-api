package com.boldenko.game_shop_api.service;

import com.boldenko.game_shop_api.entity.User;
import com.boldenko.game_shop_api.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

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
}
