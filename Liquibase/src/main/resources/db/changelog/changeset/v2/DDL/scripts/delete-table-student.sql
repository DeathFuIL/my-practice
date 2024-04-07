alter table course_students
    drop constraint fk_course_students_student_id;

delete from student;
drop table student;