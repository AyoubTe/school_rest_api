package net.samiayoub.school.dto.responses;

import java.util.Date;

public record AssignmentResponse(
    Long id,
    String name,
    String description,
    Date dueDate,
    AssignmentDetailsResponse assignmentDetailsResponse
) { }
