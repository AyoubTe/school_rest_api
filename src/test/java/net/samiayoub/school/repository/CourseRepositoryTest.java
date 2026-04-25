package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Course;
import net.samiayoub.school.entity.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void shouldCreateCourse() {
        // Arrange
        Teacher teacher = teacherRepository.findById(1L).orElse(null);
        Course course = new Course();

        course.setCode("MAT909");
        course.setName("Mathématiques 123");
        course.setTeacher(teacher);
        course.setStudents(new ArrayList<>());

        // Act
        Course savedCourse = courseRepository.save(course);

        // Assert
        assertNotNull(savedCourse);
    }

    @Test
    void shouldGetAllCourses() {
        List<Course> courses = courseRepository.findAll();

        assertNotNull(courses);
        assertEquals(21, courses.size());
    }

    @Test
    void shouldGetCourseById() {
        Course course = courseRepository.findById(1L).orElse(null);
        assertNotNull(course);
    }

    @Test
    void shouldDeleteCourse() {
        courseRepository.deleteById(1L);
        Course course = courseRepository.findById(1L).orElse(null);
        Assertions.assertNull(course);
    }

    @Test
    void shouldUpdateCourse() {
        Course existingCourse = courseRepository.findById(3L).get();

        existingCourse.setName("Advanced Calculus");
        Course saved = courseRepository.save(existingCourse);

        assertNotNull(saved);
        assertEquals("Advanced Calculus", saved.getName());
    }
}