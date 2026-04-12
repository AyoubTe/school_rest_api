package net.samiayoub.school.dto.requets;

import net.samiayoub.school.dto.responses.AssignmentResponse;

public record AssignmentDetailsRequest (
        Boolean isDone,
        AssignmentResponse assignmentResponse
) { }
