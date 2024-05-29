create extension if not exists "uuid-ossp";

create table person (
    id uuid
);

create table album (
    id uuid primary key default uuid_generate_v4(),
    name varchar(50) not null,
    description varchar(255)
);

create table podcast (
    id uuid primary key default uuid_generate_v4(),
    name varchar(50) not null,
    description varchar(255)
);

create table report (
    id uuid primary key default uuid_generate_v4(),
    theme varchar(50) not null,
    description varchar(255)
);