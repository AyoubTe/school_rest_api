package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.GradeDetailsResponse;
import net.samiayoub.school.dto.responses.StudentCourseDetailsResponse;
import net.samiayoub.school.dto.responses.StudentResponse;
import net.samiayoub.school.entity.Course;
import net.samiayoub.school.entity.Student;
import net.samiayoub.school.entity.Teacher;
import net.samiayoub.school.mapper.CourseMapper;
import net.samiayoub.school.mapper.GradeDetailsMapper;
import net.samiayoub.school.mapper.StudentMapper;
import net.samiayoub.school.repository.StudentRepository;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentMapper studentMapper;

    @Mock
    CourseMapper courseMapper;

    @Mock
    GradeDetailsMapper gradeDetailsMapper;

    @Mock
    GradeDetailsService gradeDetailsService;

    @InjectMocks
    StudentService studentService;

    @Test
    void shouldCreateStudent() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");
        StudentResponse studentDto = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student@school.net");

        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(studentDto);
        StudentResponse result = studentService.createStudent(student);

        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void shouldUpdateStudent() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");
        StudentResponse studentDto = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student@school.net");

        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(studentDto);

        StudentResponse result = studentService.updateStudent(student);
        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void shouldDeleteStudent() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");

        studentService.deleteStudent(student);

        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void shouldDeleteStudentById() {
        Long studentId = 1L;
        studentService.deleteStudentById(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void shouldGetAllStudents() {
        Student student1 = new Student(1L, "student.test", "Test", "STUDENT", "student1@school.net", "ShahedPass");
        Student student2 = new Student(2L, "student.test", "Test", "STUDENT", "student2@school.net", "ShahedPass");
        Student student3 = new Student(3L, "student.test", "Test", "STUDENT", "student3@school.net", "ShahedPass");
        StudentResponse studentDto1 = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student1@school.net");
        StudentResponse studentDto2 = new StudentResponse(2L, "student.test", "Test", "STUDENT", "student2@school.net");
        StudentResponse studentDto3 = new StudentResponse(3L, "student.test", "Test", "STUDENT", "student3@school.net");

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2, student3));
        when(studentMapper.toDtoList(studentRepository.findAll())).thenReturn(List.of(studentDto1, studentDto2, studentDto3));

        List<StudentResponse> students = studentService.getAllStudents();
        assertThat(students).isEqualTo(List.of(studentDto1, studentDto2, studentDto3));
    }

    @Test
    void shouldGetStudentById() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");
        StudentResponse studentDto = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student@school.net");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toDto(student)).thenReturn(studentDto);

        StudentResponse result = studentService.getStudentById(1L);
        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void shouldGetCourses() {
        // Arrange
        Course course1 = new Course(1L, "INF301", "Cloud Computing Infra", new Teacher(), new ArrayList<>());
        Course course2 = new Course(2L, "INF302", "Big Data", new Teacher(), new ArrayList<>());
        Course course3 = new Course(3L, "INF303", "Cloud Networking",  new Teacher(), new ArrayList<>());

        CourseResponse response1 = new CourseResponse(1L, "INF301", "Cloud Computing Infra");
        CourseResponse response2 = new CourseResponse(2L, "INF302", "Big Data");
        CourseResponse response3 = new CourseResponse(3L, "INF303", "Cloud Networking");

        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net",  "ShahedPass");
        student.setCourses(List.of(course1, course2, course3));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseMapper.toDtoList(anyList())).thenReturn(List.of(response1, response2, response3));

        // Act
        List<CourseResponse> results = studentService.getCourses(1L);

        // Assert
        assertThat(results)
                .hasSize(3)
                .isEqualTo(List.of(response1, response2, response3));
    }

    @Test
    void shouldGetGrades() {
        // Arrange
        StudentCourseDetailsResponse scdResponse = new StudentCourseDetailsResponse(1L, 1L, 1L);
        GradeDetailsResponse response1 = new GradeDetailsResponse(1L, 18, 20, 17, scdResponse);
        GradeDetailsResponse response2 = new GradeDetailsResponse(2L, 15, 10, 18, scdResponse);
        GradeDetailsResponse response3 = new GradeDetailsResponse(3L, 11, 15, 14, scdResponse);

        when(gradeDetailsMapper.toDtoList(gradeDetailsService.getGradesForStudent(1L))).thenReturn(Arrays.asList(response1, response2, response3));

        // Act
        List<GradeDetailsResponse> results = studentService.getGrades(1L);

        // Assert
        assertThat(results).isEqualTo(List.of(response1, response2, response3));
    }

    @Test
    void shouldCheckIfIsStudentExists() {
        // Arrange
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net",  "ShahedPass");
        when(studentRepository.findByUsername("student.test")).thenReturn(Optional.of(student));

        // Act
        Boolean result = studentService.isStudentExists("student.test");

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldGetStudentByUsernameOrEmail() {
        // Arrange
        when(studentRepository.findByUsernameOrEmail("unknown.username", "unkonwn.username")).thenReturn(Optional.empty());

        // Act
        Student result = studentService.getStudentByUsernameOrEmail("unknown.username");

        // Assert
        assertThat(result).isNull();
    }
}