-- liquibase formatted sql

-- changeset Boldenko Artem:1
CREATE TABLE user_image (
    user_id UUID NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, image_name),
    CONSTRAINT fk_user_image_users FOREIGN KEY (user_id) REFERENCES clients(id)
);
-- rollback DROP TABLE user_image;

-- changeset Boldenko Artem:2
CREATE TABLE company_image (
    company_id UUID NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (company_id, image_name),
    CONSTRAINT fk_company_image_companies FOREIGN KEY (company_id) REFERENCES companies(id)
);
-- rollback DROP TABLE company_image;

-- changeset Boldenko Artem:3
CREATE TABLE game_image (
    game_id UUID NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (game_id, image_name),
    CONSTRAINT fk_game_image_games FOREIGN KEY (game_id) REFERENCES games(id)
);
-- rollback DROP TABLE game_image;