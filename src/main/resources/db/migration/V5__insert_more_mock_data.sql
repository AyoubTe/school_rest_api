-- =================================================================================================
-- V5__insert_more_mock_data.sql
-- Injection de données de test avec mots de passe chiffrés en BCrypt
-- Mot de passe pour tous les comptes : password123
-- =================================================================================================

-- 1. Ajout de 5 Nouveaux Administrateurs
INSERT INTO admins (username, firstname, lastname, email, password, mission) VALUES
                                                                                 ('admin.chief', 'Paul', 'Directeur', 'paul.chief@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Supervision globale'),
                                                                                 ('admin.network', 'Alice', 'Tech', 'alice.net@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Gestion du réseau et sécurité'),
                                                                                 ('admin.payroll', 'Bob', 'Ressources', 'bob.pay@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Gestion de la paie'),
                                                                                 ('admin.admissions', 'Claire', 'Scolaire', 'claire.adm@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Gestion des admissions'),
                                                                                 ('admin.audit', 'Denis', 'Compta', 'denis.audit@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Audit financier');

-- 2. Ajout d'une nouvelle école
INSERT INTO schools (school_name, school_address) VALUES
    ('Institut Supérieur des Sciences', '12 Rue de la Recherche, Lyon');

-- 3. Ajout de nouveaux Professeurs
INSERT INTO teachers (username, firstname, lastname, email, password, discipline, school_id) VALUES
                                                                                                 ('prof.einstein', 'Albert', 'Physique', 'a.einstein@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Relativité', (SELECT MAX(id) FROM schools)),
                                                                                                 ('prof.lovelace', 'Ada', 'Code', 'a.lovelace@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Algorithmique', (SELECT MAX(id) FROM schools)),
                                                                                                 ('prof.nietzsche', 'Friedrich', 'Philo', 'f.nietzsche@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Philosophie Moderne', (SELECT MAX(id) FROM schools));

-- 4. Ajout de nouveaux Cours
INSERT INTO courses (course_code, course_name, teacher_id) VALUES
                                                               ('PHY-202', 'Physique Relativiste', (SELECT id FROM teachers WHERE username = 'prof.einstein')),
                                                               ('CS-303', 'Structures de données', (SELECT id FROM teachers WHERE username = 'prof.lovelace')),
                                                               ('PHI-404', 'Philosophie du 19ème siècle', (SELECT id FROM teachers WHERE username = 'prof.nietzsche'));

-- 5. Ajout de nouveaux Étudiants
INSERT INTO students (username, firstname, lastname, email, password) VALUES
                                                                          ('etu.link', 'Link', 'Hero', 'link.h@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.samus', 'Samus', 'Aran', 'samus.a@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.kratos', 'Kratos', 'Sparta', 'kratos.s@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.cloud', 'Cloud', 'Strife', 'cloud.s@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y');

-- 6. Création de bulletins de notes
INSERT INTO grade_details (first_grade, second_grade, third_grade) VALUES
                                                                       (15, 17, 16),
                                                                       (19, 20, 18),
                                                                       (11, 13, 12),
                                                                       (20, 19, 19);

-- 7. Inscriptions des étudiants aux nouveaux cours
INSERT INTO student_course_details (student_id, course_id, grade_details_id) VALUES
                                                                                 ((SELECT id FROM students WHERE username = 'etu.link'), (SELECT id FROM courses WHERE course_code = 'PHY-202'), (SELECT MAX(id)-3 FROM grade_details)),
                                                                                 ((SELECT id FROM students WHERE username = 'etu.samus'), (SELECT id FROM courses WHERE course_code = 'CS-303'), (SELECT MAX(id)-2 FROM grade_details)),
                                                                                 ((SELECT id FROM students WHERE username = 'etu.kratos'), (SELECT id FROM courses WHERE course_code = 'PHI-404'), (SELECT MAX(id)-1 FROM grade_details)),
                                                                                 ((SELECT id FROM students WHERE username = 'etu.cloud'), (SELECT id FROM courses WHERE course_code = 'CS-303'), (SELECT MAX(id) FROM grade_details));

-- 8. Ajout de nouveaux devoirs
INSERT INTO assignments (assignment_name, description, due_date) VALUES
                                                                     ('Devoir Relativité', 'Calcul de la dilatation du temps', '2026-06-15 23:59:00'),
                                                                     ('Projet Algorithme', 'Implémenter un arbre binaire en Java', '2026-07-01 23:59:00');

-- 9. Lier les devoirs aux nouveaux étudiants
INSERT INTO assignment_details (is_done, assignment_id, student_id) VALUES
                                                                        (false, (SELECT id FROM assignments WHERE assignment_name = 'Devoir Relativité'), (SELECT id FROM students WHERE username = 'etu.link')),
                                                                        (true, (SELECT id FROM assignments WHERE assignment_name = 'Projet Algorithme'), (SELECT id FROM students WHERE username = 'etu.samus'));