-- =================================================================================================
-- Fichier d'initialisation pour H2 (Tests d'intégration)
-- Mot de passe pour tous : password123 ($2a$10$slYQmyNdGzTn7ZLBXp45ou4y047H.M8w1h66H8C197j.y.i8v5j22)
-- =================================================================================================

-- 1. Écoles
INSERT INTO schools (school_name, school_address) VALUES
    ('Test School Academy', '123 Test Avenue, TestCity');

-- 2. Administrateurs
INSERT INTO admins (username, firstname, lastname, email, password, mission, role) VALUES
    ('admin.test', 'Alice', 'Admin', 'admin.test@school.net',
     '$2a$10$slYQmyNdGzTn7ZLBXp45ou4y047H.M8w1h66H8C197j.y.i8v5j22',
     'Testing Operations', 'ADMIN');
-- 3. Professeurs
INSERT INTO teachers (username, firstname, lastname, email, password, discipline, role, school_id) VALUES
    ('teacher.test', 'Test', 'Teacher', 'teacher.test@school.net',
     '$2a$10$slYQmyNdGzTn7ZLBXp45ou4y047H.M8w1h66H8C197j.y.i8v5j22',
     'Computer Science', 'TEACHER', 1);
-- 4. Étudiants
INSERT INTO students (username, firstname, lastname, email, password, role) VALUES
    ('student.test', 'Test', 'Student', 'student.test@school.net',
     '$2a$10$slYQmyNdGzTn7ZLBXp45ou4y047H.M8w1h66H8C197j.y.i8v5j22',
     'STUDENT');

-- 5. Cours
INSERT INTO courses (course_code, course_name, teacher_id) VALUES
    ('TEST-101', 'Introduction to Testing', 1);

-- 6. Bulletins de notes
INSERT INTO grade_details (first_grade, second_grade, third_grade) VALUES
    (15, 16, 18);

-- 7. Inscription au cours
INSERT INTO student_course_details (student_id, course_id, grade_details_id) VALUES
    (1, 1, 1);

-- 8. Devoirs
INSERT INTO assignments (assignment_name, description, due_date) VALUES
    ('Test Assignment 1', 'Write a unit test', '2026-12-31 23:59:00');

-- 9. Devoirs rendus
INSERT INTO assignment_details (is_done, assignment_id, student_id) VALUES
    (true, 1, 1);