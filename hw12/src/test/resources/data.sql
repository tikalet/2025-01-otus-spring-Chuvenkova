insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into book_comments(comment_text, book_id)
values ('comment_text_1_1', 1), ('comment_text_1_2', 1), ('comment_text_1_3', 1),
       ('comment_text_2_1', 2), ('comment_text_2_2', 2), ('comment_text_3_1', 3);

insert into users (username, password)
values
       ('user', '$2a$13$YpNzkAhUb.7uuWxJIPBenu43vbPIdfMTC9h1kk1O9cU5kuNCWva.u'),
       ('lib', '$2a$13$YpNzkAhUb.7uuWxJIPBenu43vbPIdfMTC9h1kk1O9cU5kuNCWva.u');

insert into authorities(authority)
values ('BOOK_EDITOR'),
       ('COMMENT_EDITOR');

insert into user_authority_link(user_id, authority_id)
values
       (1, 2), -- user - COMMENT_EDITOR
       (2, 1); -- lib - BOOK_EDITOR
