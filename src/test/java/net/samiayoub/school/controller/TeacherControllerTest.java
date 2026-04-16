package net.samiayoub.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.SchoolResponse;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Teacher;
import net.samiayoub.school.mapper.TeacherMapper;
import net.samiayoub.school.repository.TeacherRepository;
import net.samiayoub.school.security.SecurityConfig;
import net.samiayoub.school.security.jwt.JwtTokenProvider;
import net.samiayoub.school.service.CustomUserDetailsService;
import net.samiayoub.school.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TeacherController.class)
@Import(SecurityConfig.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TeacherMapper teacherMapper;

    @MockBean
    private TeacherRepository teacherRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private TeacherService teacherService;

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldGetTeachers() throws Exception {
        TeacherResponse teacher1 = new TeacherResponse(1L, "teacher1.test", "Test", "TEACHER1", "teacher1.test@school.net", "Mathématiques");
        TeacherResponse teacher2 = new TeacherResponse(2L, "teacher2.test", "Test", "TEACHER2", "teacher2.test@school.net", "Mathématiques");
        TeacherResponse teacher3 = new TeacherResponse(3L, "teacher3.test", "Test", "TEACHER3", "teacher3.test@school.net", "Mathématiques");

        when(teacherService.getAllTeachers()).thenReturn(List.of(teacher1, teacher2, teacher3));

        mockMvc.perform(get("/api/v1/teachers"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldNotAddTeacher() throws Exception {
        Teacher request = new Teacher(1L, "teacher1.test", "Test", "TEACHER1", "teacher1.test@school.net", "HashePass", "Mathématiques");
        TeacherResponse response = new TeacherResponse(1L, "teacher1.test", "Test", "TEACHER1", "teacher1.test@school.net", "Mathématiques");

        when(teacherService.createTeacher(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/teachers")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "username": "teacher.test",
                              "firstname": "Test",
                              "lastname": "TEACHER",
                              "email": "test.teacher@school.net",
                              "password": "HashedPassword",
                              "discipline": "Mathématiques"
                            }
                        """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldUpdateTeacher() throws Exception {
        Teacher request = new Teacher(1L, "teacher1.test", "Test", "TEACHER1", "teacher1.test@school.net", "HashePass", "Mathématiques");
        TeacherResponse response = new TeacherResponse(1L, "teacher1.test", "Test", "TEACHER1", "teacher1.test@school.net", "Mathématiques");

        when(teacherService.updateTeacher(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/teachers")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "username": "teacher.test",
                              "firstname": "Test",
                              "lastname": "TEACHER",
                              "email": "test.teacher@school.net",
                              "password": "HashedPassword",
                              "discipline": "Mathématiques"
                            }
                        """))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldNotDeleteTeacher() throws Exception {
        mockMvc.perform(delete("/api/v1/teachers/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void getTeacher() throws Exception {
        TeacherResponse response = new TeacherResponse(1L, "teacher1.test", "Test", "TEACHER1", "teacher1.test@school.net", "Mathématiques");
        when(teacherService.getTeacherById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("teacher1.test"));
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldGetCourses() throws Exception {
        // Arrange
        CourseResponse course1 = new CourseResponse(1L, "MAT1", "Mathématiques 1");
        CourseResponse course2 = new CourseResponse(2L, "MAT3", "Mathématiques 2");
        CourseResponse course3 = new CourseResponse(3L, "ALG2", "Algébre 1");

        List<CourseResponse> expectedCourses = List.of(course1, course2, course3);

        when(teacherService.getCoursesByTeacherId(1L)).thenReturn(expectedCourses);

        String expectedJson = objectMapper.writeValueAsString(expectedCourses);

        // Act & Assert
        mockMvc.perform(get("/api/v1/teachers/1/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldGetSchool() throws Exception {
        // Arrange
        SchoolResponse school = new SchoolResponse(1L, "His Majesty Ayoub I Business School", "1 SAMI Street Mars");
        when(teacherService.getSchool(1L)).thenReturn(school);

        // Act & Assert
        mockMvc.perform(get("/api/v1/teachers/1/school"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("His Majesty Ayoub I Business School"));
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldGetMyProfile() throws Exception {
        // Arrange
        TeacherResponse myProfile = new TeacherResponse(1L, "teacher.test", "Test", "TEACHER", "teacher@school.net", "Physics");

        // Le Principal prend la valeur du "username" défini dans @WithMockUser
        when(teacherService.getTeacherByUsername("teacher.test")).thenReturn(myProfile);

        // Act & Assert
        mockMvc.perform(get("/api/v1/teachers/myprofile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("teacher.test"))
                .andExpect(jsonPath("$.email").value("teacher@school.net"));
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldGetMyCourses() throws Exception {
        // Arrange
        CourseResponse course1 = new CourseResponse(1L, "MAT1", "Mathématiques 1");
        CourseResponse course2 = new CourseResponse(2L, "MAT3", "Mathématiques 2");
        CourseResponse course3 = new CourseResponse(3L, "ALG2", "Algèbre 1");

        when(teacherService.getCoursesByTeacherUsername("teacher.test"))
                .thenReturn(List.of(course1, course2, course3));

        // Act & Assert
        mockMvc.perform(get("/api/v1/teachers/mycourses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].code").value("MAT1"))
                .andExpect(jsonPath("$[1].code").value("MAT3"))
                .andExpect(jsonPath("$[2].name").value("Algèbre 1"));
    }
}