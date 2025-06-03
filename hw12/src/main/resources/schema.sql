create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

create table book_comments (
    id bigserial,
    comment_text varchar,
    book_id bigint references books(id) on delete cascade,
    primary key (id)
);

create table users (
    id bigserial,
	username varchar(255) not null,
	password varchar(255) not null,
	primary key (id)
);

create table authorities (
    id bigserial,
	authority varchar(255) not null,
	primary key (id)
);

create table user_authority_link (
    user_id bigint references users(id) on delete cascade,
    authority_id bigint references authorities(id) on delete cascade
);
