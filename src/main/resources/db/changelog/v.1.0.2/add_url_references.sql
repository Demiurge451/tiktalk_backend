alter table podcast
add column image_url varchar(1024) unique,
add column audio_url varchar(1024) unique;

alter table person
add column image_url varchar(1024) unique,
add column name varchar(255) not null;