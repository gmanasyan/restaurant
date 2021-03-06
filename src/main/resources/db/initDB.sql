DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR                           NOT NULL,
    email      VARCHAR                           NOT NULL,
    password   VARCHAR                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        TEXT      NOT NULL,
    registered   TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX restaurants_unique_name_idx
    ON restaurants (name);

CREATE TABLE dishes
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name          TEXT      NOT NULL,
    price         INT       NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    date          DATE NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE INDEX dishes_date_idx
    ON dishes (date);

CREATE INDEX dishes_date_restaurant_idx
    ON dishes (date, restaurant_id);

CREATE TABLE votes
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id       INTEGER   NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    date          DATE NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX votes_unique_date_user_idx
    ON votes (user_id, date);

CREATE TABLE history
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER   NOT NULL,
    date          DATE      NOT NULL,
    votes         INTEGER   NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX history_unique_date_restaurant_idx
    ON history (date, restaurant_id);

