alter table podcast
add column image_url varchar(1024),
add column audio_url varchar(1024);

alter table person
add column image_url varchar(1024),
add column name varchar(255) not null;