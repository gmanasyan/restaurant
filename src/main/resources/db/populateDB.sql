DELETE FROM user_roles;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('Admin', 'admin@gmail.com', '{noop}admin'),
('User1', 'user1@gmail.com', '{noop}password'),
('User2', 'user2@gmail.com', '{noop}password'),
('User3', 'user3@gmail.com', '{noop}password');

INSERT INTO restaurants (date_time, name)
VALUES ('2019-08-16 13:00:00', 'The Ivy'),
       ('2019-08-16 13:00:00', 'Cafe Pushkin'),
       ('2019-08-16 13:00:00', 'White Rabbit');

INSERT INTO dishes (restaurant_id, date, name, price)
VALUES (100005, '2019-08-16', 'Summer squash soup with creamed feta biscuits', 875),
       (100005, '2019-08-16', 'Girolle, lemon & parsley risotto', 1500),
       (100005, '2019-08-16', 'Mediterranean salad with quinoa, beetroot, datterini & olives', 2250),
       (100005, '2019-08-26', 'Summer squash soup with creamed feta biscuits', 875),
       (100005, '2019-08-26', 'Girolle, lemon & parsley risotto', 1500),
       (100005, '2019-08-26', 'Mediterranean salad with quinoa, beetroot, datterini & olives', 2250),
       (100006, '2019-08-15', 'Squash tabbouleh ', 1275),
       (100006, '2019-08-15', 'The Ivy vegetarian Shepherds Pie', 1433),
       (100006, '2019-08-15', 'Linguine primavera ', 1600),
       (100006, '2019-08-16', 'Squash tabbouleh ', 1275),
       (100006, '2019-08-16', 'The Ivy vegetarian Shepherds Pie', 1433),
       (100006, '2019-08-16', 'Linguine primavera ', 1600),
       (100005, now(), 'Summer squash soup with creamed feta biscuits', 875),
       (100005, now(), 'Girolle, lemon & parsley risotto', 1500),
       (100005, now(), 'Mediterranean salad with quinoa, beetroot, datterini & olives', 2250),
       (100006, now(), 'The Ivy vegetarian Shepherds Pie', 1433),
       (100006, now(), 'Linguine primavera ', 1600);


       INSERT INTO user_roles (role, user_id) VALUES
('ROLE_ADMIN', 100000),
('ROLE_USER', 100001),
('ROLE_USER', 100002),
('ROLE_USER', 100003);

INSERT INTO votes (user_id, restaurant_id, date)
VALUES (100003, 100005, '2019-08-26'),
       (100003, 100005, '2019-08-27'),
       (100002, 100005, '2019-08-27'),
       (100001, 100006, '2019-08-27'),
       (100002, 100005, now()),
       (100001, 100006, now());

INSERT INTO history (restaurant_id, date, votes)
VALUES (100005, '2019-08-26', 1),
       (100005, '2019-08-27', 2),
       (100006, '2019-08-27', 1),
       (100005, now(), 1),
       (100006, now(), 1);



