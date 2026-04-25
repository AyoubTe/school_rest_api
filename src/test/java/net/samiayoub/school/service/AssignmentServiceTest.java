package net.samiayoub.school.service;

import net.samiayoub.school.dto.responses.AssignmentResponse;
import net.samiayoub.school.entity.Assignment;
import net.samiayoub.school.mapper.AssignmentMapper;
import net.samiayoub.school.repository.AssignmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private AssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void shouldCreateAssignment() {
        // Arrange
        Assignment assignment = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());

        when(assignmentService.createAssignment(assignment)).thenReturn(response);

        // Act
        AssignmentResponse result = assignmentService.createAssignment(assignment);

        // Assert
        Assertions.assertEquals(response, result);
    }

    @Test
    void shouldUpdateAssignment() {
        // Arrange
        Assignment assignment = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());

        when(assignmentService.updateAssignment(assignment)).thenReturn(response);

        // Act
        AssignmentResponse result = assignmentService.updateAssignment(assignment);

        // Assert
        Assertions.assertEquals(response, result);
    }

    @Test
    void shouldDeleteAssignment() {
        Assignment assignment = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        assignmentService.deleteAssignment(assignment);

        verify(assignmentRepository, times(1)).delete(assignment);
    }

    @Test
    void shouldDeleteById() {
        assignmentService.deleteById(1L);
        verify(assignmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetAllAssignments() {
        // Arrange
        Assignment assignment1 = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        Assignment assignment2 = new Assignment(2L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response1 = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response2 = new AssignmentResponse(2L, "Homework 2", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());

        when(assignmentRepository.findAll()).thenReturn(List.of(assignment1, assignment2));
        when(assignmentMapper.toDtoList(assignmentRepository.findAll())).thenReturn(List.of(response1, response2));

        // Act
        List<AssignmentResponse> result = assignmentService.getAllAssignments();

        // Assert
        Assertions.assertEquals(List.of(response1, response2), result);
    }

    @Test
    void getAssignmentById() {
        // Arrange
        Assignment assignment = new Assignment(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());
        AssignmentResponse response = new AssignmentResponse(1L, "Homework 1", "Do all exercices 1 to 5 page 234", new Date(2026, 4, 16), new ArrayList<>());

        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(assignmentMapper.toDto(assignment)).thenReturn(response);

        // Act
        AssignmentResponse result = assignmentService.getAssignmentById(1L);

        // Assert
        Assertions.assertEquals(response, result);
    }
}