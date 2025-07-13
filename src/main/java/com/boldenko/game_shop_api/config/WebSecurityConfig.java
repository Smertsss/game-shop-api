package com.boldenko.game_shop_api.config;

import com.boldenko.game_shop_api.entity.Role;
import com.boldenko.game_shop_api.entity.User;
import com.boldenko.game_shop_api.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class WebSecurityConfig {
    private final UserServiceImpl userServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .disable()
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/main-games", "/new-games", "/top-games", "/update-games",
                                "/login", "/register", "/static/**", "/*.js", "/*.css", "/*.ico", "/assets/**",
                                "/api/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
                            response.setHeader("Access-Control-Allow-Credentials", "true");

                            // Создаем UserPreview из аутентифицированного пользователя
                            User user = (User) authentication.getPrincipal();

                            // Формируем JSON вручную
                            String jsonResponse = String.format(
                                    "{\"id\":\"%s\",\"username\":\"%s\",\"email\":\"%s\",\"roles\":[%s]}",
                                    user.getId(),
                                    user.getUsername(),
                                    user.getEmail(),
                                    user.getRoles().stream()
                                            .map(role -> "\"" + role.getName() + "\"")
                                            .collect(Collectors.joining(","))
                            );

                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write(jsonResponse);

                            log.info("Successful login - username: {}, IP: {}", authentication.getName(), request.getRemoteAddr());
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write("{\"error\": \"Invalid credentials\"}");
                            log.info("Failure login - username: {}, IP: {}", request.getParameter("username"), request.getRemoteAddr());
                        })
                        .permitAll()
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write("{\"error\": \"Not authenticated\"}");
                            log.warn("Unauthorized access attempt to {} from IP: {}",
                                    request.getRequestURI(),
                                    request.getRemoteAddr());
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write("{\"error\": \"Access denied\"}");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
                            response.setHeader("Access-Control-Allow-Credentials", "true");
                            response.getWriter().write("{\"status\":\"success\"}");

                            if (authentication != null) {
                                log.info("User {} logged out successfully", authentication.getName());
                            } else {
                                log.info("Anonymous user logged out");
                            }
                        })
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN") // Удаляем все используемые куки
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}