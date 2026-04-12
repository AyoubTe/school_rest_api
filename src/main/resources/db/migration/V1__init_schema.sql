-- ==========================================
-- V1 : Initialisation du schéma de l'école
-- ==========================================

-- 1. Tables Indépendantes
CREATE TABLE schools (
                         id BIGSERIAL PRIMARY KEY,
                         school_name VARCHAR(255) NOT NULL,
                         school_address VARCHAR(255)
);

CREATE TABLE admins (
                        id BIGSERIAL PRIMARY KEY,
                        username VARCHAR(50) UNIQUE NOT NULL,
                        firstname VARCHAR(100),
                        lastname VARCHAR(100),
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        mission VARCHAR(255)
);

CREATE TABLE teachers (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(50) UNIQUE NOT NULL,
                          firstname VARCHAR(100),
                          lastname VARCHAR(100),
                          email VARCHAR(255) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          discipline VARCHAR(255)
);

CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(50) UNIQUE NOT NULL,
                          firstname VARCHAR(100),
                          lastname VARCHAR(100),
                          email VARCHAR(255) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL
);

CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         course_code VARCHAR(50) UNIQUE NOT NULL,
                         course_name VARCHAR(255) NOT NULL
);

-- Note: Basé sur votre ancienne entité avec @Column(name = "first_grade")
CREATE TABLE grade_details (
                               id BIGSERIAL PRIMARY KEY,
                               first_grade INTEGER,
                               second_grade INTEGER,
                               third_grade INTEGER
);

CREATE TABLE assignments (
                             id BIGSERIAL PRIMARY KEY,
                             assignment_name VARCHAR(255) NOT NULL,
                             description TEXT,
                             due_date TIMESTAMP
);

-- 2. Tables de relations (Dépendent des tables ci-dessus)

CREATE TABLE student_course_details (
                                        id BIGSERIAL PRIMARY KEY,
                                        student_id BIGINT REFERENCES students(id) ON DELETE CASCADE,
                                        course_id BIGINT REFERENCES courses(id) ON DELETE CASCADE,
                                        grade_details_id BIGINT REFERENCES grade_details(id) ON DELETE SET NULL
);

CREATE TABLE assignment_details (
                                    id BIGSERIAL PRIMARY KEY,
                                    is_done BOOLEAN DEFAULT FALSE,
                                    student_id BIGINT REFERENCES students(id) ON DELETE CASCADE,
                                    assignment_id BIGINT REFERENCES assignments(id) ON DELETE CASCADE
);

-- V7__add_role_column.sql
ALTER TABLE students ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'STUDENT';
ALTER TABLE teachers ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'TEACHER';
ALTER TABLE admins   ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'ADMIN';