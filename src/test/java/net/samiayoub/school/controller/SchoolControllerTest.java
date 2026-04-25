package net.samiayoub.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.samiayoub.school.dto.requets.SchoolRequest;
import net.samiayoub.school.dto.responses.SchoolResponse;
import net.samiayoub.school.entity.School;
import net.samiayoub.school.mapper.SchoolMapper;
import net.samiayoub.school.security.SecurityConfig;
import net.samiayoub.school.security.jwt.JwtTokenProvider;
import net.samiayoub.school.service.CustomUserDetailsService;
import net.samiayoub.school.service.SchoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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

@WebMvcTest(SchoolController.class)
@Import(SecurityConfig.class)
class SchoolControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SchoolMapper schoolMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private SchoolService schoolService;

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldGetAllSchools() throws Exception {
        // Arrange
        SchoolResponse response1 = new SchoolResponse(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse");
        SchoolResponse response2 = new SchoolResponse(2L, "INSA", "Toulouse");
        SchoolResponse response3 = new SchoolResponse(3L, "TBS", "Toulouse");

        when(schoolService.getSchools()).thenReturn(Arrays.asList(response1, response2, response3));

        mockMvc.perform(get("/api/v1/schools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldNotAddSchool() throws Exception {
        // Arrange
        SchoolRequest request = new SchoolRequest("ENSEEIHT", "2 Rue Camichel, Toulouse");
        SchoolResponse response = new SchoolResponse(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse");

        when(schoolService.createSchool(schoolMapper.toEntity(request))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/schools")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "ENSEEIHT",
                                "address": "2 Rue Camichel, Toulouse"
                            }
                            """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldAddSchool() throws Exception {
        // Arrange
        SchoolRequest request = new SchoolRequest("ENSEEIHT", "2 Rue Camichel, Toulouse");
        SchoolResponse response = new SchoolResponse(1L, "ENSEEIHT", "2 Rue Camichel, Toulouse");

        when(schoolService.createSchool(schoolMapper.toEntity(request))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/schools")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "ENSEEIHT",
                                "address": "2 Rue Camichel, Toulouse"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("ENSEEIHT"));
    }

    @Test
    @WithMockUser(username = "admin.test", roles = {"ADMIN"})
    void shouldUpdateSchool() throws Exception {
        // Arrange
        School school = new School(1L, "N7", "2 Rue Camichel, Toulouse", new ArrayList<>());
        SchoolResponse response = new SchoolResponse(1L, "N7", "2 Rue Camichel, Toulouse");

        when(schoolMapper.toEntity(any(SchoolRequest.class))).thenReturn(school);
        when(schoolService.updateSchool(any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/v1/schools/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "N7",
                                "address": "2 Rue Camichel, Toulouse"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("N7"))
                .andExpect(jsonPath("$.address").value("2 Rue Camichel, Toulouse"));
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldNotDeleteSchool() throws Exception {
        mockMvc.perform(delete("/api/v1/schools/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetSchoolById() throws Exception {
        // Arrange
        SchoolResponse response = new SchoolResponse(111L, "N7", "2 Rue Camichel, Toulouse");
        when(schoolService.findSchoolById(111L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/v1/schools/111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(111L))
                .andExpect(jsonPath("$.name").value("N7"))
                .andExpect(jsonPath("$.address").value("2 Rue Camichel, Toulouse"));
    }
}