package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.SchoolResponse;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Teacher;
import net.samiayoub.school.mapper.CourseMapper;
import net.samiayoub.school.mapper.SchoolMapper;
import net.samiayoub.school.mapper.TeacherMapper;
import net.samiayoub.school.repository.TeacherRepository;
import org.assertj.core.api.Assertions;
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
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private SchoolMapper schoolMapper;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void shouldCreateTeacher() {
        Teacher teacher = new Teacher(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");
        TeacherResponse teacherDto = new TeacherResponse(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "Mathematiques");
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        TeacherResponse teacherResponse = teacherService.createTeacher(teacher);
        assertThat(teacherResponse).isEqualTo(teacherDto);
    }

    @Test
    void shouldUpdateTeacher() {
        Teacher teacher = new Teacher(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");
        TeacherResponse teacherDto = new TeacherResponse(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "Mathematiques");

        when(teacherRepository.save(teacher)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        TeacherResponse teacherResponse = teacherService.updateTeacher(teacher);
        assertThat(teacherResponse).isEqualTo(teacherDto);
    }

    @Test
    void shouldDeleteTeacher() {
        Teacher teacher = new Teacher(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");

        teacherService.deleteTeacher(teacher);

        verify(teacherRepository, times(1)).delete(teacher);
    }

    @Test
    void shouldGetAllTeachers() {
        Teacher teacher1 = new Teacher(1L, "teacher.test1", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");
        Teacher teacher2 = new Teacher(2L, "teacher.test2", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");
        Teacher teacher3 = new Teacher(3L, "teacher.test3", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");
        TeacherResponse teacherDto = new TeacherResponse(1L, "teacher.test1", "Test", "TEACHER", "teacher@school.net", "Mathematiques");
        TeacherResponse teacherDto1 = new TeacherResponse(2L, "teacher.test2", "Test", "TEACHER", "teacher@school.net", "Mathematiques");
        TeacherResponse teacherDto2 = new TeacherResponse(3L, "teacher.test3", "Test", "TEACHER", "teacher@school.net", "Mathematiques");

        when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher2, teacher3));
        when(teacherMapper.toDtoList(teacherRepository.findAll())).thenReturn(List.of(teacherDto, teacherDto1, teacherDto2));

        List<TeacherResponse> teachers = teacherService.getAllTeachers();

        Assertions.assertThat(teachers).hasSize(3);
    }

    @Test
    void shouldGetTeacherById() {
        Teacher teacher = new Teacher(13L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");
        TeacherResponse teacherDto = new TeacherResponse(13L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "Mathematiques");

        when(teacherRepository.findById(13L)).thenReturn(Optional.of(teacher));
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        TeacherResponse teacherResponse = teacherService.getTeacherById(13L);
        assertThat(teacherResponse).isEqualTo(teacherDto);
    }

    @Test
    void shouldDeleteTeacherById() {
        Long teacherId = 1L;

        teacherService.deleteTeacherById(teacherId);

        verify(teacherRepository, times(1)).deleteById(teacherId);
    }

    @Test
    void shouldGetCoursesByTeacherId() {
        // Arrange
        CourseResponse response1 = new CourseResponse(1L, "MAT301", "Algebra");
        CourseResponse response2 = new CourseResponse(1L, "MAT302", "Analytics");
        CourseResponse response3 = new CourseResponse(1L, "MAT302", "Statistics");

        Teacher teacher = new Teacher(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        when(courseMapper.toDtoList(teacher.getCourses())).thenReturn(List.of(response1, response2, response3));

        // Act
        List<CourseResponse> results = teacherService.getCoursesByTeacherId(1L);

        // Assert
        assertThat(results)
                .hasSize(3)
                .isEqualTo(List.of(response1, response2, response3));
    }

    @Test
    void shouldGetSchool() {
        // Arrange
        SchoolResponse response = new SchoolResponse(1L, "SA Business School", "2 Av Mclean, Fynor, MM");
        Teacher teacher = new Teacher(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(schoolMapper.toDto(teacher.getSchool())).thenReturn(response);

        // Act
        SchoolResponse result = teacherService.getSchool(1L);

        // Assert
        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldCheckIfTeacherExists() {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");

        when(teacherRepository.findByUsername("teacher.test")).thenReturn(Optional.of(teacher));

        // Act
        Boolean result = teacherService.isTeacherExists("teacher.test");

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void shouldGetTeacherByUsernameOrEmail() {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "password123", "Mathematiques");

        when(teacherRepository.findByUsernameOrEmail("teacher.test", "teacher.test")).thenReturn(Optional.of(teacher));

        // Act
        Teacher result = teacherService.getTeacherByUsernameOrEmail("teacher.test");

        // Assert
        assertThat(result).isEqualTo(teacher);
    }
}