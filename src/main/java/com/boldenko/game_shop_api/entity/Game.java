package com.boldenko.game_shop_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = Game.TABLE_NAME)
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    public static final String TABLE_NAME = "games";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "cost", nullable = false)
    private float cost;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @ManyToMany(mappedBy = "games")
    private Set<User> users;

    @ManyToMany(mappedBy = "likedGames")
    private Set<User> likedByUsers;

    @ManyToMany(mappedBy = "dislikedGames")
    private Set<User> dislikedByUsers;

    @ManyToMany
    @JoinTable(
            name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;
}
