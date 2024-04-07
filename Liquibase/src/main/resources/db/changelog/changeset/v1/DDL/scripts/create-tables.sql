create table student (
    id serial primary key,
    email varchar not null,
    fio varchar not null,
    birthday date not null
);

create table teacher (
    id serial primary key,
    email varchar not null,
    fio varchar not null,
    birthday date not null
);

create table course_students (
    course_id bigint not null,
    student_id bigint not null,
    constraint fk_course_students_course_id foreign key (course_id) references course(id) on delete cascade,
    constraint fk_course_students_student_id foreign key (student_id) references student(id) on delete cascade
);

create table course (
    id serial primary key,
    name varchar not null,
    teacher_id bigint not null,
    students_count bigint,
    constraint fk_course_teacher_id foreign key (teacher_id) references teacher(id) on delete cascade
);
