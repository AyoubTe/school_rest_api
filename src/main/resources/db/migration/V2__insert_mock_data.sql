-- ==========================================
-- V2 : Insertion des données de test (Mock Data)
-- ==========================================

-- 1. Les 3 Écoles
INSERT INTO schools (school_name, school_address) VALUES
                                        ('Lycée Victor Hugo', '12 Avenue des Champs-Élysées, Paris'),
                                        ('Collège Jean Moulin', '45 Rue de la République, Lyon'),
                                        ('École Primaire Marie Curie', '8 Boulevard Pasteur, Bordeaux');

-- 2. Les 14 Cours
INSERT INTO courses (course_code, course_name) VALUES
                                     ('MAT101', 'Mathématiques Avancées'),
                                     ('PHY101', 'Physique Fondamentale'),
                                     ('CHM101', 'Chimie Organique'),
                                     ('BIO101', 'Biologie et Génétique'),
                                     ('HIS101', 'Histoire Contemporaine'),
                                     ('GEO101', 'Géographie Mondiale'),
                                     ('ENG101', 'Anglais LV1'),
                                     ('ESP101', 'Espagnol LV2'),
                                     ('FRA101', 'Littérature Française'),
                                     ('INF101', 'Introduction à l''Informatique'),
                                     ('ART101', 'Arts Plastiques'),
                                     ('MUS101', 'Éducation Musicale'),
                                     ('SPO101', 'Éducation Physique et Sportive'),
                                     ('ECO101', 'Sciences Économiques et Sociales');

-- 3. Les 10 Professeurs (Teachers)
INSERT INTO teachers (username, firstname, lastname, email, password, discipline) VALUES
                                                                                      ('prof.martin', 'Paul', 'Martin', 'paul.martin@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Mathématiques Avancées'),
                                                                                      ('prof.bernard', 'Sophie', 'Bernard', 'sophie.bernard@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Physique Fondamentale'),
                                                                                      ('prof.thomas', 'Luc', 'Thomas', 'luc.thomas@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Littérature Française'),
                                                                                      ('prof.petit', 'Emma', 'Petit', 'emma.petit@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Anglais LV1'),
                                                                                      ('prof.robert', 'Marc', 'Robert', 'marc.robert@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Histoire Contemporaine'),
                                                                                      ('prof.richard', 'Julie', 'Richard', 'julie.richard@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Biologie et Génétique'),
                                                                                      ('prof.durand', 'Antoine', 'Durand', 'antoine.durand@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Introduction à l''Informatique'),
                                                                                      ('prof.dubois', 'Céline', 'Dubois', 'celine.dubois@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Sciences Économiques et Sociales'),
                                                                                      ('prof.moreau', 'Pierre', 'Moreau', 'pierre.moreau@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Arts Plastiques'),
                                                                                      ('prof.laurent', 'Alice', 'Laurent', 'alice.laurent@school.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y', 'Éducation Physique et Sportive');

-- 4. Les 20 Étudiants (Students)
INSERT INTO students (username, firstname, lastname, email, password) VALUES
                                                                          ('etu.simon', 'Lucas', 'Simon', 'lucas.simon@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.michel', 'Léa', 'Michel', 'lea.michel@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.lefebvre', 'Hugo', 'Lefebvre', 'hugo.lefebvre@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.leroy', 'Chloé', 'Leroy', 'chloe.leroy@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.roux', 'Arthur', 'Roux', 'arthur.roux@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.david', 'Camille', 'David', 'camille.david@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.bertrand', 'Enzo', 'Bertrand', 'enzo.bertrand@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.morel', 'Inès', 'Morel', 'ines.morel@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.fournier', 'Nathan', 'Fournier', 'nathan.fournier@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.girard', 'Manon', 'Girard', 'manon.girard@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.bonnet', 'Mathis', 'Bonnet', 'mathis.bonnet@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.dupont', 'Clara', 'Dupont', 'clara.dupont@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.lambert', 'Jules', 'Lambert', 'jules.lambert@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.fontaine', 'Sarah', 'Fontaine', 'sarah.fontaine@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.rousseau', 'Gabin', 'Rousseau', 'gabin.rousseau@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.vincent', 'Margaux', 'Vincent', 'margaux.vincent@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.muller', 'Raphaël', 'Muller', 'raphael.muller@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.lefevre', 'Jade', 'Lefevre', 'jade.lefevre@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.faure', 'Maël', 'Faure', 'mael.faure@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y'),
                                                                          ('etu.andre', 'Louise', 'Andre', 'louise.andre@student.net', '$2a$10$Q66lLzaHbcYQHJ/Hm6t/0OfOYbbcYLVVCeYE35ldW3ywYacGTvF2y');