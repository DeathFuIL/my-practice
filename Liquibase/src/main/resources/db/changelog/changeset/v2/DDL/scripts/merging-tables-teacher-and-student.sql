create table "user" (
    id serial primary key,
    email varchar not null,
    first_name varchar not null,
    last_name varchar not null,
    birthday date not null,
    type varchar not null
);

insert into "user" (id, email, first_name, last_name, birthday, type)
select s.id, s.email, s.birthday, 'student' as type,
       split_part(s.fio, ' ', 1) as last_name, split_part(s.fio, ' ', 2) as first_name
from student s;

insert into "user" (id, email, first_name, last_name, birthday, type)
select t.id, t.email, t.birthday, 'teacher' as type,
       split_part(t.fio, ' ', 1) as last_name, split_part(t.fio, ' ', 2) as first_name
from teacher t;

