-- liquibase formatted sql

-- changeset YourName:1
DROP TABLE IF EXISTS game_studios;
-- rollback CREATE TABLE game_studios (...);

-- changeset YourName:2
CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    context TEXT,
    creation_date DATE NOT NULL
);
-- rollback DROP TABLE companies;

-- changeset YourName:3
CREATE TABLE user_company (
    user_id UUID NOT NULL,
    company_id UUID NOT NULL,
    PRIMARY KEY (user_id, company_id),
    CONSTRAINT fk_user_company_users FOREIGN KEY (user_id) REFERENCES clients(id),
    CONSTRAINT fk_user_company_companies FOREIGN KEY (company_id) REFERENCES companies(id)
);
-- rollback DROP TABLE user_company;

-- changeset YourName:4
CREATE TABLE game_company (
    game_id UUID NOT NULL,
    company_id UUID NOT NULL,
    PRIMARY KEY (game_id, company_id),
    CONSTRAINT fk_game_company_games FOREIGN KEY (game_id) REFERENCES games(id),
    CONSTRAINT fk_game_company_companies FOREIGN KEY (company_id) REFERENCES companies(id)
);
-- rollback DROP TABLE game_company;