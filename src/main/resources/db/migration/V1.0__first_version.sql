create table usr
(
    id       bigserial    not null
        constraint user_pk
            primary key,
    username varchar(15)  not null,
    pass     varchar(100) not null
);

alter table usr
    owner to postgres;

create unique index user_id_uindex
    on usr (id);

create unique index user_login_uindex
    on usr (username);

create table organisations
(
    id   bigserial   not null
        constraint organisations_pk
            primary key,
    name varchar(40) not null
);

alter table organisations
    owner to postgres;

create unique index organisations_name_uindex
    on organisations (name);

create table bank_account
(
    id              bigserial        not null
        constraint bank_account_pk
            primary key,
    money           double precision not null,
    user_id         bigint
        constraint bank_account_usr_id_fk
            references usr
            on update cascade on delete cascade,
    organisation_id bigint
        constraint bank_account_organisations_id_fk
            references organisations
            on update cascade on delete cascade
);

alter table bank_account
    owner to postgres;

create table transaction
(
    id          bigserial not null
        constraint transaction_pk
            primary key,
    source      bigint    not null
        constraint transaction_bank_account_id_fk
            references bank_account
            on update cascade on delete cascade,
    destination bigint    not null
        constraint transaction_bank_account_id_fk_2
            references bank_account
            on update cascade on delete cascade,
    val         integer   not null
);

alter table transaction
    owner to postgres;

create unique index transaction_id_uindex
    on transaction (id);

create unique index bank_account_id_uindex
    on bank_account (id);

create table roles
(
    id   bigserial   not null
        constraint roles_pk
            primary key,
    name varchar(15) not null
);

alter table roles
    owner to postgres;

create unique index roles_id_uindex
    on roles (id);

create unique index roles_name_uindex
    on roles (name);

create table usr_role
(
    id      serial not null
        constraint usr_role_pk
            primary key,
    user_id bigint
        constraint usr_role_usr_id_fk
            references usr
            on update cascade on delete cascade,
    role_id bigint
        constraint usr_role_roles_id_fk
            references roles
            on update cascade on delete cascade
);

alter table usr_role
    owner to postgres;

create unique index usr_role_id_uindex
    on usr_role (id);

INSERT INTO "roles" ("name")
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO "usr" ("username", "pass")
VALUES ('user', '$2a$11$ow2sj0B3c506jyUXMaaoku2Ie2PBSmEIKnMxGg14qsY5ZByI8Fs2S'),
       ('user1', '$2a$11$.rKH6L6OTtX/Orv/CyZA1.u7oSgbqCLUCFBCOqfu3vLGtfNbjswb6'),
       ('admin', '$2a$11$78oD6dK9CfB.5OX7Nzajpexpbz04K/N2TbK2vm.IwELlpd1.fATk2'),
       ('apple', '$2a$11$tJRoIbBcMHuqs/tSOXSzSuJjlXXlsIevsV7oJGMzrdqOX9/lPgRne');

INSERT INTO "organisations" ("name")
VALUES ('Apple');

INSERT INTO "bank_account" ("money", "user_id", "organisation_id")
VALUES (100000, 1, NULL),
       (100000, 2, NULL),
       (100000, 3, NULL),
       (100000, 4, 1);

INSERT INTO "usr_role" ("user_id", "role_id")
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 1);

