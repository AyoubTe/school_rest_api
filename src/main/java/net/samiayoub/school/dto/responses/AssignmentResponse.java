package net.samiayoub.school.dto.responses;

import java.util.Date;
import java.util.List;

public record AssignmentResponse(
    Long id,
    String name,
    String description,
    Date dueDate,
    List<AssignmentDetailsResponse> assignmentDetailsResponse
) { }
