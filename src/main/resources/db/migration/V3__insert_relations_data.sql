-- ==========================================
-- V3 : Insertion des relations (Notes, Inscriptions, Devoirs)
-- ==========================================

-- 1. Création de Notes (Grade Details) - 20 bulletins aléatoires
INSERT INTO grade_details (first_grade, second_grade, third_grade) VALUES
                                                                       (14, 15, 16), (10, 12, 11), (18, 17, 19), (9, 10, 12), (15, 14, 16),
                                                                       (11, 13, 12), (16, 18, 17), (12, 12, 13), (19, 20, 18), (8, 9, 10),
                                                                       (14, 14, 15), (13, 11, 14), (17, 16, 18), (10, 10, 11), (15, 16, 15),
                                                                       (12, 14, 13), (18, 19, 19), (11, 10, 12), (16, 15, 17), (14, 13, 15);

-- 2. Inscriptions des étudiants aux cours (Student Course Details)
-- On lie les IDs des étudiants (1 à 20), les IDs des cours (1 à 14) et les IDs des notes (1 à 20)
INSERT INTO student_course_details (student_id, course_id, grade_details_id) VALUES
                                                                                 (1, 1, 1),  (1, 2, 2),  (1, 3, 3),   -- Lucas Simon prend Maths, Physique, Chimie
                                                                                 (2, 1, 4),  (2, 4, 5),  (2, 5, 6),   -- Léa Michel prend Maths, Biologie, Histoire
                                                                                 (3, 7, 7),  (3, 8, 8),  (3, 9, 9),   -- Hugo Lefebvre prend Anglais, Espagnol, Français
                                                                                 (4, 10, 10),(4, 11, 11),(4, 12, 12), -- Chloé Leroy prend Informatique, Arts, Musique
                                                                                 (5, 1, 13), (5, 13, 14),(5, 14, 15), -- Arthur Roux prend Maths, Sport, Eco
                                                                                 (6, 2, 16), (7, 3, 17), (8, 4, 18),  -- Autres assignations basiques...
                                                                                 (9, 5, 19), (10, 6, 20);

-- 3. Création de Devoirs (Assignments)
INSERT INTO assignments (assignment_name, description, due_date) VALUES
                                                          ('Devoir Maison de Mathématiques', 'Résoudre les équations du chapitre 4.', '2026-04-15 08:00:00'),
                                                          ('Dissertation d''Histoire', 'Les causes de la révolution industrielle.', '2026-04-20 12:00:00'),
                                                          ('Projet d''Informatique', 'Créer une API REST avec Spring Boot.', '2026-05-01 23:59:00'),
                                                          ('Exposé d''Anglais', 'Présenter un pays anglophone de votre choix.', '2026-04-18 10:00:00');

-- 4. Suivi des devoirs par étudiant (Assignment Details)
-- is_done définit si l'étudiant a rendu son devoir ou non
INSERT INTO assignment_details (is_done, student_id, assignment_id) VALUES
                                                                        (true, 1, 1),  (false, 1, 2), (false, 1, 3), -- Lucas a rendu les maths, pas le reste
                                                                        (true, 2, 1),  (true, 2, 2),  (false, 2, 4), -- Léa a rendu maths et histoire
                                                                        (false, 3, 1), (false, 3, 2), (false, 3, 3), -- Hugo n'a rien rendu
                                                                        (true, 4, 3),  (true, 4, 4),                 -- Chloé a rendu info et anglais
                                                                        (true, 5, 1),  (false, 5, 3),
                                                                        (true, 6, 1),  (true, 7, 2),  (false, 8, 3),
                                                                        (true, 9, 4),  (false, 10, 1);