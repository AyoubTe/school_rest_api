package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Teacher;
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
    void getCourseByTeacherId() {
    }

    @Test
    void getSchool() {

    }

    @Test
    void isTeacherExists() {
    }

    @Test
    void getTeacherByUsernameOrEmail() {
    }
}