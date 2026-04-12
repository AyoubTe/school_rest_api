package net.samiayoub.school.controller;

import net.samiayoub.school.dto.requets.AssignmentRequest;
import net.samiayoub.school.dto.responses.AssignmentResponse;
import net.samiayoub.school.mapper.AssignmentMapper;
import net.samiayoub.school.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final AssignmentMapper assignmentMapper;

    public AssignmentController(AssignmentService assignmentService, AssignmentMapper assignmentMapper) {
        this.assignmentService = assignmentService;
        this.assignmentMapper = assignmentMapper;
    }

    @GetMapping
    public List<AssignmentResponse> getAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentResponse createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.createAssignment(assignmentMapper.toEntity(assignmentRequest));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentResponse updateAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.updateAssignment(assignmentMapper.toEntity(assignmentRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable Long id) {
        assignmentService.getAssignmentById(id);
    }

    @GetMapping("/{id}")
    public AssignmentResponse getAssigment(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }
}
