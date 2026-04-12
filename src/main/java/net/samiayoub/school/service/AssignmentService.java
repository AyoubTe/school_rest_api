package net.samiayoub.school.service;

import jakarta.persistence.EntityNotFoundException;
import net.samiayoub.school.dto.responses.AssignmentResponse;
import net.samiayoub.school.entity.Assignment;
import net.samiayoub.school.mapper.AssignmentMapper;
import net.samiayoub.school.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    public AssignmentResponse createAssignment(Assignment assignment) {
        return assignmentMapper.toDto(assignmentRepository.save(assignment));
    }

    public AssignmentResponse updateAssignment(Assignment assignment) {
        return assignmentMapper.toDto(assignmentRepository.save(assignment));
    }

    public void deleteAssignment(Assignment assignment) {
        assignmentRepository.delete(assignment);
    }

    public void deleteById(Long id) {
        assignmentRepository.deleteById(id);
    }

    public List<AssignmentResponse> getAllAssignments() {
        return assignmentMapper.toDtoList(assignmentRepository.findAll());
    }

    public AssignmentResponse getAssignmentById(Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment == null) {
            throw new EntityNotFoundException("Assignment with id: " + id + " not found");
        }
        return assignmentMapper.toDto(assignment);
    }
}
