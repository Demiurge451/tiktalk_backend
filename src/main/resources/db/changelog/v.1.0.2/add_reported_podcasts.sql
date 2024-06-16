create table reported_podcast(
    id uuid primary key default uuid_generate_v4(),
    creation_date date default now(),
    name varchar(50) not null,
    description varchar(255),
    album_id uuid not null,
    person_id uuid not null,
    verdict varchar(1024) not null,
    image_url varchar(1024),
    audio_url varchar(1024),
    foreign key (person_id) references person(id),
    foreign key (album_id) references album(id)
);