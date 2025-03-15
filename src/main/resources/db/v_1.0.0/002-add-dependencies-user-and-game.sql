-- liquibase formatted sql

-- changeset Boldenko Artem:1
ALTER TABLE clients DROP COLUMN role_id;
ALTER TABLE games DROP COLUMN genre_id;
ALTER TABLE games DROP COLUMN like_id;
ALTER TABLE games DROP COLUMN dislike_id;
-- rollback ALTER TABLE clients ADD COLUMN role_id UUID NOT NULL;
-- rollback ALTER TABLE games ADD COLUMN genre_id UUID;
-- rollback ALTER TABLE games ADD COLUMN like_id UUID;
-- rollback ALTER TABLE games ADD COLUMN dislike_id UUID;

-- changeset Boldenko Artem:2
CREATE TABLE game_genre (
    game_id UUID NOT NULL,
    genre_id UUID NOT NULL,
    PRIMARY KEY (game_id, genre_id),
    CONSTRAINT fk_game_genre_games FOREIGN KEY (game_id) REFERENCES games(id),
    CONSTRAINT fk_game_genre_genres FOREIGN KEY (genre_id) REFERENCES genres(id)
);
-- rollback DROP TABLE game_genre;

-- changeset Boldenko Artem:3
CREATE TABLE user_game (
    user_id UUID NOT NULL,
    game_id UUID NOT NULL,
    PRIMARY KEY (user_id, game_id),
    CONSTRAINT fk_user_game_users FOREIGN KEY (user_id) REFERENCES clients(id),
    CONSTRAINT fk_user_game_games FOREIGN KEY (game_id) REFERENCES games(id)
);
-- rollback DROP TABLE user_game;

-- changeset Boldenko Artem:4
CREATE TABLE user_game_like (
    user_id UUID NOT NULL,
    game_id UUID NOT NULL,
    PRIMARY KEY (user_id, game_id),
    CONSTRAINT fk_user_game_like_users FOREIGN KEY (user_id) REFERENCES clients(id),
    CONSTRAINT fk_user_game_like_games FOREIGN KEY (game_id) REFERENCES games(id)
);
-- rollback DROP TABLE user_game_like;

-- changeset Boldenko Artem:5
CREATE TABLE user_game_dislike (
    user_id UUID NOT NULL,
    game_id UUID NOT NULL,
    PRIMARY KEY (user_id, game_id),
    CONSTRAINT fk_user_game_dislike_users FOREIGN KEY (user_id) REFERENCES clients(id),
    CONSTRAINT fk_user_game_dislike_games FOREIGN KEY (game_id) REFERENCES games(id)
);
-- rollback DROP TABLE user_game_dislike;

-- changeset Boldenko Artem:6
CREATE TABLE user_role (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_users FOREIGN KEY (user_id) REFERENCES clients(id),
    CONSTRAINT fk_user_role_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);
-- rollback DROP TABLE user_role;
