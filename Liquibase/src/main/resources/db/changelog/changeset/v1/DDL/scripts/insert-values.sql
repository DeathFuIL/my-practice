insert into teacher (email, fio, birthday) values
    ('teacher1@example.com', 'Иванов Иван Иванович', '1990-01-01'),
    ('teacher2@example.com', 'Петров Петр Петрович', '1985-05-15');

insert into student (email, fio, birthday) values
    ('student1@example.com', 'Сидоров Александр Васильевич', '2000-03-10'),
    ('student2@example.com', 'Петрова Ольга Ивановна', '2001-07-20'),
    ('student3@example.com', 'Иванова Екатерина Сергеевна', '1999-11-05'),
    ('student4@example.com', 'Новиков Дмитрий Андреевич', '2002-09-15'),
    ('student5@example.com', 'Кузнецов Артем Михайлович', '1998-12-30'),
    ('student6@example.com', 'Смирнова Анастасия Петровна', '2003-02-25'),
    ('student7@example.com', 'Морозов Илья Алексеевич', '1997-04-17'),
    ('student8@example.com', 'Волкова Алена Дмитриевна', '2000-06-12'),
    ('student9@example.com', 'Козлов Максим Николаевич', '1999-08-08'),
    ('student10@example.com', 'Лебедева Виктория Владимировна', '2001-10-22');

insert into course (name, teacher_id, students_count) values
    ('Математика', 1, 3),
    ('Информатика', 2, 4),
    ('Физика', 1, 2);

insert into course_students (course_id, student_id) values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 7),
    (3, 8),
    (3, 9);