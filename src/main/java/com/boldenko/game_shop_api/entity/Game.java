package com.boldenko.game_shop_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = Game.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    public static final String TABLE_NAME = "games";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "creationCompanyId", nullable = false)
    private UUID creationCompanyId;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "cost", nullable = false)
    private float cost;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "cost", nullable = false)
    private int like;

    @Column(name = "cost", nullable = false)
    private int dislike;

    public UUID getId() {
        return id;
    }
}
