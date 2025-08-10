package com.boldenko.game_shop_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@Table(name = Company.TABLE_NAME)
public class Company {
    public static final String TABLE_NAME = "companies";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = Fields.id, updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String context;

    @Column(name = Fields.creationDate, nullable = false)
    private LocalDate creationDate;

    @ManyToMany(mappedBy = "companies")
    private Set<User> users;

    @ManyToMany(mappedBy = "companies")
    private Set<Game> games;
}
