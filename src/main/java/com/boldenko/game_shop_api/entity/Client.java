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
@Table(name = Client.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    public static final String TABLE_NAME = "clients";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "firstName", nullable = false, length = 20)
    private String firstName;

    @Column(name = "secondName", nullable = false, length = 20)
    private String secondName;

    @Column(name = "username", nullable = false, length = 33, unique = true)
    private String username;

    @Column(name = "email", nullable = false, length = 33, unique = true)
    private String email;

    @Column(name = "login", nullable = false, length = 33)
    private String login;

    @Column(name = "password", nullable = false, length = 33)
    private String password;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "lastLoginDate", nullable = false)
    private LocalDateTime lastLoginDate;

    @Column(name = "online", nullable = false)
    private boolean online;

    @Column(name = "moderator", nullable = false)
    private boolean moderator;

    public UUID getId() {
        return id;
    }
}
