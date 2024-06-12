create extension if not exists "uuid-ossp";

drop table if exists person, album, podcast, report;

create table person (
    id uuid primary key
);

create table followers (
    follower_id uuid not null,
    author_id uuid not null,
    foreign key (follower_id) references person(id),
    foreign key (author_id) references person(id),
    primary key (follower_id, author_id)
);

create table album (
    id uuid primary key default uuid_generate_v4(),
    title varchar(50) not null,
    description varchar(255),
    person_id uuid not null,
    foreign key (person_id) references person(id)
);

create table podcast (
    id uuid primary key default uuid_generate_v4(),
    name varchar(50) not null,
    description varchar(255),
    album_id uuid not null,
    person_id uuid not null,
    creation_date date default now(),
    foreign key (person_id) references person(id),
    foreign key (album_id) references album(id)
);

create table liked_podcasts (
    person_id uuid not null,
    podcast_id uuid not null,
    foreign key (person_id) references person(id),
    foreign key (podcast_id) references podcast(id),
    primary key (person_id, podcast_id)
);

create table report (
    id uuid primary key default uuid_generate_v4(),
    theme varchar(50) not null,
    description varchar(255),
    reporter_id uuid not null,
    podcast_id uuid not null,
    foreign key (reporter_id) references person(id),
    foreign key (podcast_id) references podcast(id)
);




