package net.samiayoub.school.dto.requets;

import java.util.Date;

public record AssignmentRequest(
        String name,
        String description,
        Date dueDate,
        AssignmentDetailsRequest details
) {
}
