package net.samiayoub.school.dto.responses;

public record AssignmentDetailsResponse (
        Long id,
        Boolean isDone,
        AssignmentResponse assignmentResponse
){ }
