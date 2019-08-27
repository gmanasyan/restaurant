DELETE FROM user_roles;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User1', 'user1@yandex.ru', 'password'),
('User2', 'user2@yandex.ru', 'password'),
('User3', 'user3@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001);

INSERT INTO restaurants (date_time, name)
VALUES ('2019-08-16 13:00:00', 'The Ivy'),
       ('2019-08-16 13:00:00', 'Cafe Pushkin'),
       ('2019-08-16 13:00:00', 'White Rabbit');

INSERT INTO dishes (restaurant_id, date, name, price)
VALUES (100005, '2019-08-16', 'Summer squash soup with creamed feta biscuits', 875),
       (100005, '2019-08-16', 'Girolle, lemon & parsley risotto', 1500),
       (100005, '2019-08-16', 'Mediterranean salad with quinoa, beetroot, datterini & olives', 2250),
       (100006, '2019-08-15', 'Squash tabbouleh ', 1275),
       (100006, '2019-08-15', 'The Ivy vegetarian Shepherds Pie', 1433),
       (100006, '2019-08-15', 'Linguine primavera ', 1600);


INSERT INTO votes (user_id, restaurant_id, date)
VALUES (100000, 100005, '2019-08-26 10:09:00'),
       (100000, 100005, '2019-08-27 10:09:00'),
       (100001, 100006, '2019-08-27 10:09:00');

