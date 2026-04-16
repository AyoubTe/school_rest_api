package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.StudentResponse;
import net.samiayoub.school.entity.Student;
import net.samiayoub.school.mapper.StudentMapper;
import net.samiayoub.school.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentMapper studentMapper;

    @InjectMocks
    StudentService studentService;

    @Test
    void createStudent() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");
        StudentResponse studentDto = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student@school.net");

        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(studentDto);
        StudentResponse result = studentService.createStudent(student);

        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void updateStudent() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");
        StudentResponse studentDto = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student@school.net");

        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(studentDto);

        StudentResponse result = studentService.updateStudent(student);
        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void deleteStudent() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");

        studentService.deleteStudent(student);

        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void deleteStudentById() {
        Long studentId = 1L;
        studentService.deleteStudentById(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void getAllStudents() {
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
    void getStudentById() {
        Student student = new Student(1L, "student.test", "Test", "STUDENT", "student@school.net", "ShahedPass");
        StudentResponse studentDto = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student@school.net");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toDto(student)).thenReturn(studentDto);

        StudentResponse result = studentService.getStudentById(1L);
        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void getCourses() {
    }

    @Test
    void getGrades() {
    }

    @Test
    void isStudentExists() {
    }

    @Test
    void getStudentByUsernameOrEmail() {
    }
}