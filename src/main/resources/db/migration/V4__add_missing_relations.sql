-- 1. Ajout de la clé étrangère teacher_id dans la table courses
ALTER TABLE courses ADD COLUMN teacher_id BIGINT REFERENCES teachers(id) ON DELETE SET NULL;

-- 2. Ajout de la clé étrangère school_id dans la table teachers (car votre Teacher est sûrement lié à School)
ALTER TABLE teachers ADD COLUMN school_id BIGINT REFERENCES schools(id) ON DELETE SET NULL;

-- 3. Mise à jour de quelques données pour lier les cours aux profs existants
UPDATE courses SET teacher_id = 1 WHERE id IN (1, 2, 3);
UPDATE courses SET teacher_id = 2 WHERE id IN (4, 5, 6);
UPDATE courses SET teacher_id = 3 WHERE id IN (7, 8, 9);
UPDATE courses SET teacher_id = 4 WHERE id IN (10, 11, 12);
UPDATE courses SET teacher_id = 5 WHERE id IN (13, 14);

-- 4. Liaison des professeurs aux écoles (Using subqueries to guarantee the ID exists)
UPDATE teachers
SET school_id = (SELECT id FROM schools WHERE school_name = 'Lycée Victor Hugo' LIMIT 1)
WHERE id IN (1, 2, 3, 4);

UPDATE teachers
SET school_id = (SELECT id FROM schools WHERE school_name = 'Collège Jean Moulin' LIMIT 1)
WHERE id IN (5, 6, 7);

UPDATE teachers
SET school_id = (SELECT id FROM schools WHERE school_name = 'École Primaire Marie Curie' LIMIT 1)
WHERE id IN (8, 9, 10);