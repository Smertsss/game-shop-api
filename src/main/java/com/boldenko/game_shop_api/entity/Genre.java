package com.boldenko.game_shop_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Table(name = Genre.TABLE_NAME)
public class Genre {
    public static final String TABLE_NAME = "genres";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = Fields.id, updatable = false, nullable = false)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Game> games;
}
