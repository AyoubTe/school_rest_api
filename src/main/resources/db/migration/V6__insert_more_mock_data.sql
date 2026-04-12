-- =================================================================================================
-- V6__insert_more_mock_data.sql
-- Injection de données de test avec mots de passe chiffrés en BCrypt
-- Mot de passe pour tous les comptes : password123
-- =================================================================================================

-- 1. Ajout de 5 Administrateurs
INSERT INTO admins (username, firstname, lastname, email, password, mission) VALUES
                                                                                 ('admin.dupont',   'Pierre',    'Dupont',    'pierre.dupont@school.net',   '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Supervision pédagogique'),
                                                                                 ('admin.martin',   'Claire',    'Martin',    'claire.martin@school.net',   '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Gestion infrastructure'),
                                                                                 ('admin.leroy',    'Thomas',    'Leroy',     'thomas.leroy@school.net',    '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Relations avec les professeurs'),
                                                                                 ('admin.bernard',  'Nathalie',  'Bernard',   'nathalie.bernard@school.net','$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Suivi des inscriptions'),
                                                                                 ('admin.petit',    'François',  'Petit',     'francois.petit@school.net',  '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Gestion budgétaire');

-- 2. Ajout d'une nouvelle école
INSERT INTO schools (school_name, school_address) VALUES
    ('Institut Numérique du Sud', '12 Rue des Lumières, Lyon');

-- 3. Ajout de nouveaux Professeurs (Changement des noms pour éviter les doublons avec V5)
INSERT INTO teachers (username, firstname, lastname, email, password, discipline, school_id) VALUES
                                                                                                 ('prof.hopper',  'Grace',   'Hopper',  'grace.hopper@school.net',  '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Mathématiques',      (SELECT MAX(id) FROM schools)),
                                                                                                 ('prof.pasteur', 'Louis',   'Pasteur', 'louis.pasteur@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Biologie Évolutive', (SELECT MAX(id) FROM schools)),
                                                                                                 ('prof.feynman', 'Richard', 'Feynman', 'richard.feynman@school.net','$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Physique Théorique', (SELECT MAX(id) FROM schools));

-- 4. Ajout de nouveaux Cours (PHY-202 existait déjà, changé en PHY-303)
INSERT INTO courses (course_code, course_name, teacher_id) VALUES
                                                               ('MATH-301', 'Algèbre et Logique Formelle',       (SELECT id FROM teachers WHERE username = 'prof.hopper')),
                                                               ('BIO-101',  'Évolution et Génétique',            (SELECT id FROM teachers WHERE username = 'prof.pasteur')),
                                                               ('PHY-303',  'Mécanique Quantique Avancée',       (SELECT id FROM teachers WHERE username = 'prof.feynman'));

-- 5. Ajout de nouveaux Étudiants (Changement pour éviter Kratos, Samus, Link qui sont dans V5)
INSERT INTO students (username, firstname, lastname, email, password) VALUES
                                                                          ('etu.geralt',  'Geralt',  'Rivia',     'geralt.r@student.net',  '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.aloy',    'Aloy',    'Nora',      'aloy.n@student.net',    '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.jill',    'Jill',    'Valentine', 'jill.v@student.net',    '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.cortana', 'Cortana', 'Spartan',   'cortana.s@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y');

-- 6. Création de bulletins de notes
INSERT INTO grade_details (first_grade, second_grade, third_grade) VALUES
                                                                       (17, 15, 16),
                                                                       (12, 14, 13),
                                                                       (19, 20, 18),
                                                                       (11, 10, 12);

-- 7. Inscriptions des étudiants aux cours
INSERT INTO student_course_details (student_id, course_id, grade_details_id) VALUES
                                                                                 ((SELECT id FROM students WHERE username = 'etu.geralt'),  (SELECT id FROM courses WHERE course_code = 'MATH-301'), (SELECT MAX(id)-3 FROM grade_details)),
                                                                                 ((SELECT id FROM students WHERE username = 'etu.aloy'),    (SELECT id FROM courses WHERE course_code = 'BIO-101'),  (SELECT MAX(id)-2 FROM grade_details)),
                                                                                 ((SELECT id FROM students WHERE username = 'etu.jill'),    (SELECT id FROM courses WHERE course_code = 'PHY-303'),  (SELECT MAX(id)-1 FROM grade_details)),
                                                                                 ((SELECT id FROM students WHERE username = 'etu.cortana'), (SELECT id FROM courses WHERE course_code = 'MATH-301'), (SELECT MAX(id)   FROM grade_details));

-- 8. Ajout de quelques devoirs
INSERT INTO assignments (assignment_name, description, due_date) VALUES
                                                                     ('Devoir Maths 1',   'Démontrer le théorème de Gödel par récurrence',          '2026-05-20 23:59:00'),
                                                                     ('Rapport Biologie', 'Analyse comparative des mécanismes d''adaptation génétique', '2026-06-10 23:59:00');

-- 9. Lier les devoirs aux étudiants
INSERT INTO assignment_details (is_done, assignment_id, student_id) VALUES
                                                                        (false, (SELECT id FROM assignments WHERE assignment_name = 'Devoir Maths 1'),   (SELECT id FROM students WHERE username = 'etu.geralt')),
                                                                        (true,  (SELECT id FROM assignments WHERE assignment_name = 'Rapport Biologie'), (SELECT id FROM students WHERE username = 'etu.aloy'));