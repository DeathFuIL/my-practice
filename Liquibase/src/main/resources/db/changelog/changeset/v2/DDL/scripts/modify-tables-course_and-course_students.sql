alter table course
    drop column students_count;

alter table course_students
    rename column student_id to user_id,
    add constraint fk_course_students_user_id foreign key (user_id) references "user" (id),
    add column type varchar not null default '';

