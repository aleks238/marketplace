create table users(
    id          bigserial primary key,
    username    varchar (30) not null unique,
    password    varchar(80) not null,
    email       varchar(50) unique,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into users(username, password, email)
values ('Bob', '$2a$12$YRFAHf.pPkBudwShuxYaOePHByuLOaBticPL55puEkcf44a7cpQNa', '@111'),
       ('Jack', '$2a$12$Y6tzD4.GdgiywYs6BanFiOZK453VlzjmQe9jkVzUci28gWenjO3I.', '@222');

create table roles(
    id              bigserial primary key,
    name            varchar(30) not null,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into roles(name) values ('ROLE_USER'),('ROLE_ADMIN');

create table users_roles(
    user_id         bigint not null references users(id),
    role_id         bigint not null references roles(id),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    primary key     (user_id,role_id)
);
insert into users_roles (user_id, role_id) values (1,1),(2,2);