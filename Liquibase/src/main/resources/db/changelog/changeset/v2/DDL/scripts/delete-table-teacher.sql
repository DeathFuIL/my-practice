alter table course
    drop constraint fk_course_teacher_id,
    drop column teacher_id;

delete from teacher;
drop table teacher;
