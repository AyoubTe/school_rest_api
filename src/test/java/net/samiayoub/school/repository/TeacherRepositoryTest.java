package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Teacher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void shouldFindByUsername() {
        Teacher teacher = teacherRepository.findByUsername("teacher.test").get();

        Assertions.assertThat(teacher.getFirstname()).isEqualTo("Test");
    }

    @Test
    void shouldFindByEmail() {
        Teacher teacher = teacherRepository.findByEmail("teacher.test@school.net").get();

        Assertions.assertThat(teacher.getFirstname()).isEqualTo("Test");
    }

    @Test
    void shouldFindByUsernameOrEmail() {
        Teacher teacher = teacherRepository.findByUsernameOrEmail("wrong.username", "teacher.test@school.net").get();

        Assertions.assertThat(teacher.getFirstname()).isEqualTo("Test");
    }
}