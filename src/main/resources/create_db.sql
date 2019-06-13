create database postgres
    with owner postgres;

comment on database postgres is 'default administrative connection database';

create table mydatabase.usr
(
    id          bigserial   not null
        constraint user_pk
            primary key,
    username    varchar(15) not null,
    pass        varchar(20) not null,
    is_admin    boolean     not null,
    money_count integer
);

alter table mydatabase.usr
    owner to postgres;

create unique index user_id_uindex
    on mydatabase.usr (id);

create unique index user_login_uindex
    on mydatabase.usr (username);

create table mydatabase.transaction
(
    id          bigserial   not null
        constraint transaction_pk
            primary key,
    from_user   varchar(45) not null,
    to_user     varchar(45) not null,
    money_count varchar(45) not null
);

alter table mydatabase.transaction
    owner to postgres;

create unique index transaction_id_uindex
    on mydatabase.transaction (id);

create table mydatabase.organisations
(
    id      bigserial   not null
        constraint organisations_pk
            primary key,
    name    varchar(40) not null,
    user_id bigint      not null
        constraint organisations_usr_id_fk
            references mydatabase.usr
            on update cascade on delete cascade
);

alter table mydatabase.organisations
    owner to postgres;

create unique index organisations_name_uindex
    on mydatabase.organisations (name);

