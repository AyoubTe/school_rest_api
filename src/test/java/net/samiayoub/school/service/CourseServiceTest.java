package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.StudentResponse;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Course;
import net.samiayoub.school.entity.Student;
import net.samiayoub.school.entity.Teacher;
import net.samiayoub.school.mapper.CourseMapper;
import net.samiayoub.school.mapper.StudentMapper;
import net.samiayoub.school.mapper.TeacherMapper;
import net.samiayoub.school.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CourseServiceTest {

    @Mock
    CourseRepository courseRepository;

    @Mock
    CourseMapper courseMapper;

    @Mock
    StudentMapper studentMapper;

    @Mock
    TeacherMapper teacherMapper;

    @InjectMocks
    CourseService courseService;

    @Test
    void shouldCreateCourse() {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");
        Course course = new Course(1L, "INF303", "Cloud Computing Infra",teacher, new ArrayList<>());
        CourseResponse courseResponse = new CourseResponse(1L, "INF303", "Cloud Computing Infra");

        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(courseResponse);

        // Act
        CourseResponse result = courseService.createCourse(course);

        //
        assertThat(result).isEqualTo(courseResponse);
    }

    @Test
    void shouldUpdateCourse() {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");
        Course course = new Course(1L, "INF303", "Cloud Computing Infra",teacher, new ArrayList<>());
        CourseResponse courseResponse = new CourseResponse(1L, "INF303", "Cloud Computing Infra");

        when(courseMapper.toDto(course)).thenReturn(courseResponse);
        when(courseRepository.save(course)).thenReturn(course);

        // Act
        CourseResponse result = courseService.updateCourse(course);

        // Assert
        assertThat(result).isEqualTo(courseResponse);
    }

    @Test
    void shouldDeleteCourse() {
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");
        Course course = new Course(1L, "INF303", "Cloud Computing Infra",teacher, new ArrayList<>());

        courseService.deleteCourse(course);

        verify(courseRepository).delete(course);
    }

    @Test
    void shouldDeleteCourseById() {
        Long courseId = 1L;

        courseService.deleteCourseById(courseId);

        verify(courseRepository).deleteById(courseId);
    }

    @Test
        void shouldGetAllCourses() {
            // Arrange
            Teacher teacher1 = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");
            Course course1 = new Course(1L, "INF303", "Cloud Computing Infra",teacher1, new ArrayList<>());
            CourseResponse courseResponse1 = new CourseResponse(1L, "INF301", "Cloud Computing Infra");

            Teacher teacher2 = new Teacher(2L, "teacher.test", "Ayoub", "SAMII", "sami.teacher@school.net", "password", "Computer Science");
            Course course2 = new Course(2L, "INF303", "Cloud Computing Infra",teacher2, new ArrayList<>());
            CourseResponse courseResponse2 = new CourseResponse(2L, "INF302", "Big Data");

            Teacher teacher3 = new Teacher(3L, "teacher.test", "Ayoub", "SALMI", "sami.teacher@school.net", "password", "Computer Science");
            Course course3 = new Course(1L, "INF303", "Cloud Computing Infra",teacher3, new ArrayList<>());
            CourseResponse courseResponse3 = new CourseResponse(3L, "INF303", "Cloud Networking");

            when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2, course3));
            when(courseMapper.toDtoList(courseRepository.findAll())).thenReturn(Arrays.asList(courseResponse1, courseResponse2, courseResponse3));

            // Act
            List<CourseResponse> result = courseService.findAllCourses();

            // Assert
            assertThat(result).isEqualTo(Arrays.asList(courseResponse1, courseResponse2, courseResponse3));
        }

    @Test
    void shouldFindCourseById() {
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");
        Course course = new Course(1L, "INF303", "Cloud Computing Infra",teacher, new ArrayList<>());
        CourseResponse courseResponse = new CourseResponse(1L, "INF303", "Cloud Computing Infra");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseResponse);

        CourseResponse result = courseService.findCourseById(1L);

        assertThat(result).isEqualTo(courseResponse);
    }

    @Test
    void getEnrolledStudents() {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");

        Student student1 = new Student(1L, "student1.test", "Test1", "STUDENT", "student1@school.net", "password");
        Student student2 = new Student(2L, "student1.test", "Test1", "STUDENT", "student1@school.net", "password");
        Student student3 = new Student(3L, "student1.test", "Test1", "STUDENT", "student1@school.net", "password");
        StudentResponse studentResponse1 = new StudentResponse(1L, "student1.test", "Test1", "STUDENT", "student1@school.net");
        StudentResponse studentResponse2 = new StudentResponse(2L, "student1.test", "Test1", "STUDENT", "student1@school.net");
        StudentResponse studentResponse3 = new StudentResponse(3L, "student1.test", "Test1", "STUDENT", "student1@school.net");

        Course course = new Course(1L, "INF303", "Cloud Computing Infra",teacher, List.of(student1, student2, student3));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(studentMapper.toDtoList(List.of(student1, student2, student3))).thenReturn(Arrays.asList(studentResponse1, studentResponse2, studentResponse3));

        // Act
        List<StudentResponse> results = courseService.getEnrolledStudents(1L);

        // Assert
        assertThat(results).isEqualTo(Arrays.asList(studentResponse1, studentResponse2, studentResponse3));
    }

    @Test
    void shouldGetTeacher() {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");
        TeacherResponse teacherResponse = new TeacherResponse(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "Computer Science");
        Student student1 = new Student(1L, "student1.test", "Test1", "STUDENT", "student1@school.net", "password");
        Student student2 = new Student(2L, "student1.test", "Test1", "STUDENT", "student1@school.net", "password");
        Student student3 = new Student(3L, "student1.test", "Test1", "STUDENT", "student1@school.net", "password");

        Course course = new Course(1L, "INF303", "Cloud Computing Infra",teacher, List.of(student1, student2, student3));

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(teacherMapper.toDto(teacher)).thenReturn(teacherResponse);

        // Act
        TeacherResponse result = courseService.getTeacher(1L);

        // Assert
        assertThat(result).isEqualTo(teacherResponse);
    }
}