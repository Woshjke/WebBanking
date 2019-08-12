INSERT INTO usr (username, pass, status, activation_code) VALUES ('admin', '$2a$11$78oD6dK9CfB.5OX7Nzajpexpbz04K/N2TbK2vm.IwELlpd1.fATk2', 'active', '');
INSERT INTO usr (username, pass, status, activation_code) VALUES ('apple', '$2a$11$tJRoIbBcMHuqs/tSOXSzSuJjlXXlsIevsV7oJGMzrdqOX9/lPgRne', 'active', '');
INSERT INTO usr (username, pass, status, activation_code) VALUES ('user', '$2a$11$ow2sj0B3c506jyUXMaaoku2Ie2PBSmEIKnMxGg14qsY5ZByI8Fs2S', 'active', '');
INSERT INTO usr (username, pass, status, activation_code) VALUES ('user2', '$2a$11$zBMrh4oA4hUZWmV07BiewuRLm91YhWCgYbg30e/qdcPgC5gVRUQ3y', 'active', '');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO usr_role (user_id, role_id) VALUES (1, 2);
INSERT INTO usr_role (user_id, role_id) VALUES (2, 1);
INSERT INTO usr_role (user_id, role_id) VALUES (3, 1);
INSERT INTO usr_role (user_id, role_id) VALUES (4, 1);

INSERT INTO organisations (name) VALUES ('Apple');
INSERT INTO organisations (name) VALUES ('Microsoft');
INSERT INTO organisations (name) VALUES ('Beltelecom');

INSERT INTO bank_account (money, user_id, organisation_id, card_number) VALUES (100000, 1, null, '2111111111111111');
INSERT INTO bank_account (money, user_id, organisation_id, card_number) VALUES (100500, 2, null, '2122222222222222');
INSERT INTO bank_account (money, user_id, organisation_id, card_number) VALUES (99500, 3, null, '2102360320576813');
INSERT INTO bank_account (money, user_id, organisation_id, card_number) VALUES (100000, 4, 1, '2144444444444444');
