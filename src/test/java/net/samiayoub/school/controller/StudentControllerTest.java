package net.samiayoub.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.samiayoub.school.dto.requets.StudentRequest;
import net.samiayoub.school.dto.responses.CourseResponse;
import net.samiayoub.school.dto.responses.GradeDetailsResponse;
import net.samiayoub.school.dto.responses.StudentCourseDetailsResponse;
import net.samiayoub.school.dto.responses.StudentResponse;
import net.samiayoub.school.entity.Student;
import net.samiayoub.school.mapper.StudentMapper;
import net.samiayoub.school.security.SecurityConfig;
import net.samiayoub.school.security.jwt.JwtTokenProvider;
import net.samiayoub.school.service.CustomUserDetailsService;
import net.samiayoub.school.service.StudentService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@Import(SecurityConfig.class)
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentMapper studentMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private StudentService studentService;


    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldNotGetAllStudents() throws Exception {
        StudentResponse studentDto1 = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student1@school.net");
        StudentResponse studentDto2 = new StudentResponse(2L, "student.test", "Test", "STUDENT", "student2@school.net");
        StudentResponse studentDto3 = new StudentResponse(3L, "student.test", "Test", "STUDENT", "student3@school.net");

        when(studentService.getAllStudents()).thenReturn(List.of(studentDto1, studentDto2, studentDto3));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldGetAllStudents() throws Exception {
        StudentResponse studentDto1 = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student1@school.net");
        StudentResponse studentDto2 = new StudentResponse(2L, "student.test", "Test", "STUDENT", "student2@school.net");
        StudentResponse studentDto3 = new StudentResponse(3L, "student.test", "Test", "STUDENT", "student3@school.net");

        when(studentService.getAllStudents()).thenReturn(List.of(studentDto1, studentDto2, studentDto3));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldAddStudent() throws Exception {
        StudentRequest request = new StudentRequest("student.test", "Test", "STUDENT", "student.test@school.net", "HashedPassword");
        StudentResponse response = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student.test@school.net");

        when(studentService.createStudent(studentMapper.toEntity(request))).thenReturn(response);

        mockMvc.perform(post("/api/v1/students")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "student.test",
                                  "firstname": "Test",
                                  "lastname": "STUDENT",
                                  "email": "test.student@school.net",
                                  "password": "HashedPassword"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("student.test"));
    }


    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldNotUpdateStudent() throws Exception {
        Student request = new Student(1L, "student.test", "Test", "STUDENT", "student.test@school.net", "HashedPassword");
        StudentResponse response = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student.test@school.net");

        when(studentService.updateStudent(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/students")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "student.test",
                                  "firstname": "Test",
                                  "lastname": "STUDENT",
                                  "email": "test.student@school.net",
                                  "password": "HashedPassword"
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldNotDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldGetStudentById() throws Exception {
        StudentResponse response = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student.test@school.net");
        when(studentService.getStudentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("student.test"));
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldGetCourses() throws Exception {
        // Arrange
        CourseResponse course1 = new CourseResponse(1L, "MAT1", "Mathématiques 1");
        CourseResponse course2 = new CourseResponse(2L, "MAT3", "Mathématiques 2");
        CourseResponse course3 = new CourseResponse(3L, "ALG2", "Algébre 1");

        List<CourseResponse> expectedCourses = List.of(course1, course2, course3);

        when(studentService.getCourses(1L)).thenReturn(expectedCourses);

        String expectedJson = objectMapper.writeValueAsString(expectedCourses);

        // Act & Assert
        mockMvc.perform(get("/api/v1/students/1/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldGetMyProfile() throws Exception {
        // Arrange
        StudentResponse myProfile = new StudentResponse(1L, "student.test", "Test", "STUDENT", "student@school.net");

        // Le Principal prend la valeur du "username" défini dans @WithMockUser
        when(studentService.getStudentByUsername("student.test")).thenReturn(myProfile);

        // Act & Assert
        mockMvc.perform(get("/api/v1/students/myprofile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("student.test"))
                .andExpect(jsonPath("$.email").value("student@school.net"));
    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldGetMyCourses() throws Exception {
        // Arrange
        CourseResponse course1 = new CourseResponse(1L, "MAT1", "Mathématiques 1");
        CourseResponse course2 = new CourseResponse(2L, "MAT3", "Mathématiques 2");
        CourseResponse course3 = new CourseResponse(3L, "ALG2", "Algèbre 1");

        when(studentService.getCoursesByStudentUsername("student.test"))
                .thenReturn(List.of(course1, course2, course3));

        // Act & Assert
        mockMvc.perform(get("/api/v1/students/mycourses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].code").value("MAT1"))
                .andExpect(jsonPath("$[1].code").value("MAT3"))
                .andExpect(jsonPath("$[2].name").value("Algèbre 1"));
    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldGetMyGrades() throws Exception {
        // Arrange
        StudentCourseDetailsResponse details1 = new StudentCourseDetailsResponse(1L, 1L, 1L);
        StudentCourseDetailsResponse details2 = new StudentCourseDetailsResponse(2L, 1L, 2L);
        GradeDetailsResponse grade1 = new GradeDetailsResponse(1L, 15, 14, 16, details1);
        GradeDetailsResponse grade2 = new GradeDetailsResponse(2L, 10, 12, 11, details2);

        when(studentService.getGradesByStudentUsername("student.test"))
                .thenReturn(List.of(grade1, grade2));

        // Act & Assert
        mockMvc.perform(get("/api/v1/students/mygrades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                // Update these lines with the correct property names:
                .andExpect(jsonPath("$[0].gradeOne").value(15))    // Used to be 'firstGrade'
                .andExpect(jsonPath("$[1].gradeTwo").value(12));   // Used to be 'secondGrade'
    }
}