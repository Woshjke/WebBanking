create table mydatabase.usr
(
    id       bigserial    not null
        constraint user_pk
            primary key,
    username varchar(15)  not null,
    pass     varchar(100) not null
);

alter table mydatabase.usr
    owner to postgres;

create unique index user_id_uindex
    on mydatabase.usr (id);

create unique index user_login_uindex
    on mydatabase.usr (username);

create table mydatabase.organisations
(
    id   bigserial   not null
        constraint organisations_pk
            primary key,
    name varchar(40) not null
);

alter table mydatabase.organisations
    owner to postgres;

create unique index organisations_name_uindex
    on mydatabase.organisations (name);

create table mydatabase.bank_account
(
    id              bigserial        not null
        constraint bank_account_pk
            primary key,
    money           double precision not null,
    user_id         bigint
        constraint bank_account_usr_id_fk
            references mydatabase.usr
            on update cascade on delete cascade,
    organisation_id bigint
        constraint bank_account_organisations_id_fk
            references mydatabase.organisations
            on update cascade on delete cascade
);

alter table mydatabase.bank_account
    owner to postgres;

create table mydatabase.transaction
(
    id          bigserial not null
        constraint transaction_pk
            primary key,
    source      bigint    not null
        constraint transaction_bank_account_id_fk
            references mydatabase.bank_account
            on update cascade on delete cascade,
    destination bigint    not null
        constraint transaction_bank_account_id_fk_2
            references mydatabase.bank_account
            on update cascade on delete cascade,
    val         integer   not null
);

alter table mydatabase.transaction
    owner to postgres;

create unique index transaction_id_uindex
    on mydatabase.transaction (id);

create unique index bank_account_id_uindex
    on mydatabase.bank_account (id);

create table mydatabase.roles
(
    id   bigserial   not null
        constraint roles_pk
            primary key,
    name varchar(15) not null
);

alter table mydatabase.roles
    owner to postgres;

create unique index roles_id_uindex
    on mydatabase.roles (id);

create unique index roles_name_uindex
    on mydatabase.roles (name);

create table mydatabase.usr_role
(
    id      serial not null
        constraint usr_role_pk
            primary key,
    user_id bigint
        constraint usr_role_usr_id_fk
            references mydatabase.usr
            on update cascade on delete cascade,
    role_id bigint
        constraint usr_role_roles_id_fk
            references mydatabase.roles
            on update cascade on delete cascade
);

alter table mydatabase.usr_role
    owner to postgres;

create unique index usr_role_id_uindex
    on mydatabase.usr_role (id);

