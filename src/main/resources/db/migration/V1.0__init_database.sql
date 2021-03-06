create table usr
(
    id bigserial not null
        constraint user_pk
            primary key,
    username varchar(15) not null,
    pass varchar(100) not null,
    status varchar(10) not null,
    activation_code varchar
);

alter table usr owner to postgres;

create unique index user_id_uindex
    on usr (id);

create unique index user_login_uindex
    on usr (username);

create table organisations
(
    id bigserial not null
        constraint organisations_pk
            primary key,
    name varchar(40) not null
);

alter table organisations owner to postgres;

create unique index organisations_name_uindex
    on organisations (name);

create table bank_account
(
    id bigserial not null
        constraint bank_account_pk
            primary key,
    money double precision not null,
    user_id bigint
        constraint bank_account_usr_id_fk
            references usr
            on update cascade on delete cascade,
    organisation_id bigint
        constraint bank_account_organisations_id_fk
            references organisations
            on update cascade on delete cascade,
    card_number varchar not null
);

alter table bank_account owner to postgres;

create unique index bank_account_id_uindex
    on bank_account (id);

create table transaction
(
    id bigserial not null
        constraint transaction_pk
            primary key,
    source bigint not null
        constraint transaction_bank_account_id_fk
            references bank_account
            on update cascade on delete cascade,
    destination bigint not null
        constraint transaction_bank_account_id_fk_2
            references bank_account
            on update cascade on delete cascade,
    val integer not null
);

alter table transaction owner to postgres;

create unique index transaction_id_uindex
    on transaction (id);

create table roles
(
    id bigserial not null
        constraint roles_pk
            primary key,
    name varchar(15) not null
);

alter table roles owner to postgres;

create unique index roles_id_uindex
    on roles (id);

create unique index roles_name_uindex
    on roles (name);

create table usr_role
(
    id serial not null
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

alter table usr_role owner to postgres;

create unique index usr_role_id_uindex
    on usr_role (id);

create table user_details
(
    id bigserial not null
        constraint user_details_pk
            primary key,
    first_name varchar,
    last_name varchar,
    phone_number varchar,
    dob varchar,
    pass_id varchar,
    user_id bigint
        constraint user_details_usr_id_fk
            references usr
            on update cascade on delete cascade,
    profile_image bytea,
    email varchar
);

alter table user_details owner to postgres;

create unique index user_details_id_uindex
    on user_details (id);

