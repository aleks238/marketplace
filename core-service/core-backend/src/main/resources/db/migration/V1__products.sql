create table categories(
    id              bigserial primary key,
    title           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);
insert into categories(title) values ('laptops'),('mobile phones');

create table products(
    id              bigserial primary key,
    title           varchar(255),
    price           int,
    category_id     bigint references categories(id),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);
insert into products(title, price, category_id) values ('ASUS ROG Strix G17', 35899, 1), ('Acer Aspire 5 A515-45-R74Z', 45854, 1),
                                                       ('Acer Aspire 5 A515-56-32DK Slim Laptop', 58959, 1), ('Acer Swift 3 Thin & Light Laptop', 48999,1);

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

create table orders (
    id              bigserial primary key,
    /*user_id         bigint not null references users(id) on delete cascade,*/
    username        varchar(255) not null,
    total_price     int not null,
    address         varchar(255),
    phone           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);


/*Заказ состоит из элементов - items. Order_items создаем из dto Cart*/
  create table order_items(
      id                    bigserial primary key,
      product_id            bigint references products(id),
      order_id              bigint references orders(id),
      quantity              int,
      price_per_product     int,
      price                 int,
      created_at            timestamp default current_timestamp,
      updated_at            timestamp default current_timestamp
);




