-- liquibase formatted sql

-- changeset Boldenko Artem:1
CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
-- rollback DROP TABLE roles;

-- changeset Boldenko Artem:2
CREATE TABLE genres (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
-- rollback DROP TABLE genres;

-- changeset Boldenko Artem:3
CREATE TABLE users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    second_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    last_login_date DATE NOT NULL,
    online BOOLEAN NOT NULL,
    role_id UUID NOT NULL,
    CONSTRAINT fk_users_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);
-- rollback DROP TABLE users;

-- changeset Boldenko Artem:4
CREATE TABLE games (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    context TEXT,
    cost BIGINT NOT NULL,
    creation_date DATE NOT NULL,
    update_date DATE NOT NULL,
    genre_id UUID,
    like_id UUID,
    dislike_id UUID,
    CONSTRAINT fk_games_genres FOREIGN KEY (genre_id) REFERENCES genres(id),
    CONSTRAINT fk_games_likes FOREIGN KEY (like_id) REFERENCES users(id),
    CONSTRAINT fk_games_dislikes FOREIGN KEY (dislike_id) REFERENCES users(id)
);
-- rollback DROP TABLE games;

-- changeset Boldenko Artem:5
CREATE TABLE game_studios (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    context TEXT,
    creation_date DATE NOT NULL,
    employees_id UUID,
    creation_game_id UUID,
    CONSTRAINT fk_game_studios_employees FOREIGN KEY (employees_id) REFERENCES users(id),
    CONSTRAINT fk_game_studios_creation_games FOREIGN KEY (creation_game_id) REFERENCES games(id)
);
-- rollback DROP TABLE game_studios;