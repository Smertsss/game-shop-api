package com.boldenko.game_shop_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = Genre.TABLE_NAME)
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    public static final String TABLE_NAME = "genres";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Game> games;
}
