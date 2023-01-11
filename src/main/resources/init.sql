INSERT INTO roles VALUES (1, 'ROLE_ADMIN'),
                         (2, 'ROLE_USER');

INSERT INTO users VALUES (1, 'admin@gmail.com', 'Ivan', '$2a$10$JY4Wl4HIvu0JETZJviMFTe78GPzB3eDj81Z6r/AcjhtHxs9W01P9a', 'Ivanov', 'admin'),
                         (2, 'user@gmail.com', 'Petr', '$2a$10$0kOIt0ryI/o9zZVbJoRvje.FvTWs5Y0WZS/hMuNpYlxME7oYscXkC', 'Petrov', 'user'),
                         (3, 'user1@gmail.com', 'Semen', '$2a$10$kyVjfYoBB0Sc2O3bkBhoWuEQaZAampzqgLFqWvOO7aM5ogTGQIdr2', 'Semenov', 'user1');

INSERT INTO users_roles VALUES (1, 1),
                               (2, 2),
                               (3, 1),
                               (3, 2);