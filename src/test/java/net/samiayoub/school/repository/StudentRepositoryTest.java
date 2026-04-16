package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldFindAllStudents() {
        // Act
        List<Student> students = studentRepository.findAll();
        Assertions.assertNotNull(students);

        // Asser
        Assertions.assertEquals(29, students.size());
    }

    @Test
    void shouldFindByUsername() {
        // Arrange

        // Act
        Student student = studentRepository.findByUsername("student.test").orElse(null);

        // Assert
        Assertions.assertNotNull(student);
    }

    @Test
    void shouldFindByEmail() {
        // Act
        Student student = studentRepository.findByEmail("student@school.net").orElse(null);

        // Assert
        Assertions.assertNull(student);
    }

    @Test
    void shouldFindByUsernameOrEmail() {
        // Act
        Student student = studentRepository.findByUsernameOrEmail("student", "student.test@school.net").orElse(null);

        // Assert
        Assertions.assertNotNull(student);
    }
}