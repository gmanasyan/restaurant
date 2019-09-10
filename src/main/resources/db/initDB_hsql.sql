DROP TABLE user_roles IF EXISTS ;
DROP TABLE history IF EXISTS ;
DROP TABLE votes IF EXISTS ;
DROP TABLE users IF EXISTS ;
DROP TABLE dishes IF EXISTS ;
DROP TABLE restaurants IF EXISTS ;

DROP SEQUENCE GLOBAL_SEQ IF EXISTS ;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)                          NOT NULL,
    email      VARCHAR(255)                           NOT NULL,
    password   VARCHAR(255)                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name        VARCHAR(255)       NOT NULL,
    date_time   TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX restaurants_unique_name_idx
    ON restaurants (name);

CREATE TABLE dishes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name          VARCHAR(255)      NOT NULL,
    price         INT       NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    date          DATE NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE INDEX dishes_date_idx
    ON dishes (date);

CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    user_id       INTEGER   NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    date          DATE NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX votes_unique_date_user_idx
    ON votes (date, user_id);

CREATE TABLE history
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER   NOT NULL,
    date          DATE      NOT NULL,
    votes         INTEGER   NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX history_unique_date_restaurant_idx
    ON history (date, restaurant_id);

