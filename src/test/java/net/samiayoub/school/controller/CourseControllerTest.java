package net.samiayoub.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.samiayoub.school.dto.requets.CourseRequest;
import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.StudentResponse;
import net.samiayoub.school.dto.responses.TeacherResponse;
import net.samiayoub.school.entity.Course;
import net.samiayoub.school.entity.Teacher;
import net.samiayoub.school.mapper.CourseMapper;
import net.samiayoub.school.security.SecurityConfig;
import net.samiayoub.school.security.jwt.JwtTokenProvider;
import net.samiayoub.school.service.CourseService;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourseController.class)
@Import(SecurityConfig.class)
class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseMapper courseMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    TeacherService teacherService;

    @MockBean
    private CourseService courseService;

    @Test
    @WithMockUser(username = "teacher.test", roles = {"STUDENT"})
    void shouldGetCourses() throws Exception {
        // Arrange
        CourseResponse courseResponse1 = new CourseResponse(1L, "INF301", "Cloud Computing Infra");
        CourseResponse courseResponse2 = new CourseResponse(2L, "INF302", "Big Data");
        CourseResponse courseResponse3 = new CourseResponse(3L, "INF303", "Cloud Networking");

        when(courseService.findAllCourses()).thenReturn(Arrays.asList(courseResponse1, courseResponse2, courseResponse3));

        // Act & Assert
        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[1].id").value(2));

    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldNotAddCourse() throws Exception {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");

        CourseRequest request = new CourseRequest("INF301", "Spring Boot JUnit Testing");
        CourseResponse response = new CourseResponse(1L, "INF301", "Cloud Computing Infra");

        when(teacherService.getTeacherByUsernameOrEmail(any())).thenReturn(teacher);
        when(courseService.createCourse(courseMapper.toEntity(request))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/courses")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "code": "INF301",
                      "name": "Spring Boot JUnit Testing"
                    }
                    """))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldAddCourse() throws Exception {
        // Arrange
        Teacher teacher = new Teacher(1L, "teacher.test", "Ayoub", "SAMI", "sami.teacher@school.net", "password", "Computer Science");

        CourseRequest request = new CourseRequest("INF301", "Spring Boot JUnit Testing");

        Course entity = new Course();
        entity.setCode("INF301");
        entity.setName("Spring Boot JUnit Testing");

        CourseResponse response = new CourseResponse(1L, "INF301", "Cloud Computing Infra");

        when(teacherService.getTeacherByUsernameOrEmail(any())).thenReturn(teacher);

        when(courseMapper.toEntity(request)).thenReturn(entity);

        when(courseService.createCourse(entity)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/courses")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "code": "INF301",
                              "name": "Spring Boot JUnit Testing"
                            }
                         """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldUpdateCourse() throws Exception {
        // Arrange
        CourseResponse response = new CourseResponse(1L, "INF301", "Spring Boot JUnit Testing");

        when(courseService.updateCourse(any(Course.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/v1/courses")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "id": 1,
                              "code": "INF301",
                              "name": "Spring Boot JUnit Testing"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("INF301"))
                .andExpect(jsonPath("$.name").value("Spring Boot JUnit Testing"));
    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldNotDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetCourse() throws Exception {
        // Arrange
        CourseResponse response = new CourseResponse(1L, "INF301", "Spring Boot JUnit Testing");

        when(courseService.findCourseById(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/v1/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("INF301"));
    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldNotGetStudents() throws Exception {
        // Arrange
        StudentResponse studentDto1 = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student1@school.net");
        StudentResponse studentDto2 = new StudentResponse(2L, "student.test", "Test", "STUDENT", "student2@school.net");
        StudentResponse studentDto3 = new StudentResponse(3L, "student.test", "Test", "STUDENT", "student3@school.net");

        when(courseService.getEnrolledStudents(any())).thenReturn(Arrays.asList(studentDto1, studentDto2, studentDto3));

        // Act and Assert
        mockMvc.perform(get("/api/v1/courses/1/students"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldGetStudents() throws Exception {
        // Arrange
        StudentResponse studentDto1 = new StudentResponse(1L, "student.test1", "Test1", "STUDENT1", "student1@school.net");
        StudentResponse studentDto2 = new StudentResponse(2L, "student.test2", "Test2", "STUDENT2", "student2@school.net");
        StudentResponse studentDto3 = new StudentResponse(3L, "student.test3", "Test3", "STUDENT3", "student3@school.net");

        when(courseService.getEnrolledStudents(any())).thenReturn(Arrays.asList(studentDto1, studentDto2, studentDto3));

        // Act & Assert
        mockMvc.perform(get("/api/v1/courses/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].username").value("student.test1"));
    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldGetTeacher() throws Exception {
        // Arrange
        TeacherResponse response = new TeacherResponse(1L, "teacher.test", "Test", "TEACHER", "test.teacher@school.net", "Mathematics");
        when(courseService.getTeacher(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/v1/courses/1/teacher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("teacher.test"));
    }
}