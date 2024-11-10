create table if not exists dz.users (
    id bigserial primary key not null ,
    username varchar(25),
    password varchar(100),
    email varchar(20),
    role varchar(20)
);

create table if not exists dz.images
(
  id bigserial primary key not null ,
    created date,
    size integer,
    user_id bigint references dz.users(id)
);

create table if not exists dz.authorities (
    id bigserial not null primary key ,
    user_id bigint references dz.users(id),
    name varchar(50)
);