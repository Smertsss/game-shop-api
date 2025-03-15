package com.boldenko.game_shop_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Table(name = Game.TABLE_NAME)
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game {
    public static final String TABLE_NAME = "games";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = Fields.id, updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String context;
    private float cost;

    @Column(name = Fields.creationDate, nullable = false)
    private LocalDateTime creationDate;

    @Column(name = Fields.updateDate, nullable = false)
    private LocalDateTime updateDate;

    @ManyToMany(mappedBy = "games")
    private Set<User> users;

    @ManyToMany(mappedBy = "likedGames")
    private Set<User> likedByUsers;

    @ManyToMany(mappedBy = "dislikedGames")
    private Set<User> dislikedByUsers;

    @ManyToMany
    @JoinTable(
            name = "game_company",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private Set<Company> companies;

    @ManyToMany
    @JoinTable(
            name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;
}
