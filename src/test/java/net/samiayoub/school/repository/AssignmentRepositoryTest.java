package net.samiayoub.school.repository;

import net.samiayoub.school.entity.Assignment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AssignmentRepositoryTest {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    void shouldGetAllAssignments() {
        // Act
        List<Assignment> assignments = assignmentRepository.findAll();

        // Assert
        Assertions.assertNotNull(assignments);
    }

    @Test
    void shouldFindById() {
        // Act
        Assignment assignment = assignmentRepository.findById(1L).orElse(null);

        // Assert
        Assertions.assertNotNull(assignment);
    }

    @Test
    void shouldSaveAssignment() {
        // Arrange
        Assignment assignment = new Assignment(1L, "Assignment", "Description of the assignment", new Date(), new ArrayList<>());

        // Act
        Assignment result = assignmentRepository.save(assignment);

        // Asser
        Assertions.assertNotNull(result);
    }

    @Test
    void shouldUpdateAssignment() {
        // Act
        Assignment assignment = assignmentRepository.findById(1L).get();
        assignment.setDescription("Updated Description of the assignment");
        Assignment result = assignmentRepository.save(assignment);

        // Assert
        Assertions.assertEquals("Updated Description of the assignment", result.getDescription());
    }

    @Test
    void shouldDeleteAssignment() {
        // Act
        Assignment assignment = assignmentRepository.findById(1L).get();
        assignmentRepository.delete(assignment);

        // Assert
        Assertions.assertNull(assignmentRepository.findById(1L).orElse(null));
    }
}