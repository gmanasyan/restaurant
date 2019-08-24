DELETE FROM user_roles;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001);

INSERT INTO restaurants (date_time, name,  menu_published)
VALUES ('2019-08-16 13:00:00', 'The Ivy', '2019-08-16 13:00:00'),
       ('2019-08-16 13:00:00', 'Cafe Pushkin', NULL),
       ('2019-08-16 13:00:00', 'White Rabbit', NULL);

INSERT INTO dishes (restaurant_id, date_time, name,  price)
VALUES (100002, '2019-08-16 13:00:00', 'Summer squash soup with creamed feta biscuits', 875),
       (100002, '2019-08-16 13:01:00', 'Girolle, lemon & parsley risotto', 1500),
       (100002, '2019-08-16 13:02:00', 'Mediterranean salad with quinoa, beetroot, datterini & olives', 1475);
