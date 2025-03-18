package com.boldenko.game_shop_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Entity
@Table(name = User.TABLE_NAME)
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    public static final String TABLE_NAME = "clients";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = Fields.id, updatable = false, nullable = false)
    private UUID id;

    private String firstName;
    private String secondName;

    @Column(name = Fields.username, nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = Fields.email, nullable = false, unique = true, length = 32)
    private String email;

    @Column(name = Fields.login, nullable = false, unique = true, length = 32)
    private String login;
    private String password;

    @Column(name = Fields.creationDate, nullable = false)
    private LocalDateTime creationDate;

    @Column(name = Fields.lastLoginDate, nullable = false)
    private LocalDateTime lastLoginDate;

    @Column(name = Fields.online, nullable = false)
    private boolean online;

    @ManyToMany
    @JoinTable(
            name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> games;

    @ManyToMany
    @JoinTable(
            name = "user_game_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> likedGames;

    @ManyToMany
    @JoinTable(
            name = "user_game_dislike",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> dislikedGames;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "user_company",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private Set<Company> companies;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEnabled() {
        return online;
    }
}
