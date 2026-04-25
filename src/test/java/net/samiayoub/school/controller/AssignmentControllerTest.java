package net.samiayoub.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.samiayoub.school.dto.requets.AssignmentRequest;
import net.samiayoub.school.dto.responses.AssignmentResponse;
import net.samiayoub.school.entity.Assignment;
import net.samiayoub.school.mapper.AssignmentMapper;
import net.samiayoub.school.security.SecurityConfig;
import net.samiayoub.school.security.jwt.JwtTokenProvider;
import net.samiayoub.school.service.AssignmentService;
import net.samiayoub.school.service.CustomUserDetailsService;
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
import java.util.Date;

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


@WebMvcTest(AssignmentController.class)
@Import(SecurityConfig.class)
class AssignmentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private AssignmentMapper assignmentMapper;

    @MockBean
    private AssignmentService assignmentService;

    @Test
    @WithMockUser(username = "student.test", roles = "STUDENT")
    void shouldGetAssignments() throws Exception {
        // Arrange
        AssignmentResponse response1 = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response2 = new AssignmentResponse(2L, "Homework 2", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response3 = new AssignmentResponse(3L, "Homework 3", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());

        when(assignmentService.getAllAssignments()).thenReturn(Arrays.asList(response1, response2, response3));

        // Act and Assert
        mockMvc.perform(get("/api/v1/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("Homework 1"));

    }

    @Test
    @WithMockUser(username = "student.test", roles = {"STUDENT"})
    void shouldNotCreateAssignment() throws Exception {
        // Arrange
        Assignment assignment = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());

        when(assignmentService.createAssignment(assignment)).thenReturn(response);

        // Act and Assert
        mockMvc.perform(post("/api/v1/assignments")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "name": "Homework 1",
                      "description": "Do all exercices 1 to 5 page 234",
                      "dueDate": "2026-04-16",
                      "details": []
                    }
                  """)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "teacher.test", roles = {"TEACHER"})
    void shouldUpdateAssignment() throws Exception {
        // Arrange
        Assignment assignment = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());

        when(assignmentMapper.toEntity(any(AssignmentRequest.class))).thenReturn(assignment);
        when(assignmentService.updateAssignment(any())).thenReturn(response);

        // Act and Assert
        mockMvc.perform(put("/api/v1/assignments/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "name": "Homework 1",
                      "description": "Do all exercices 1 to 5 page 234",
                      "dueDate": "2026-04-16",
                      "details": []
                    }
                  """)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteAssignment() throws Exception {
        mockMvc.perform(delete("/api/v1/assignments/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetAssigment() throws Exception {
        Assignment assignment = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        when(assignmentMapper.toDto(assignment)).thenReturn(response);
        when(assignmentService.getAssignmentById(1L)).thenReturn(response);

        // Act and Assert
        mockMvc.perform(get("/api/v1/assignments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Homework 1"));
    }
}