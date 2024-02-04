create table person (
    id serial primary key,
    login varchar(50) not null unique,
    password varchar(50) not null
)