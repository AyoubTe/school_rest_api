package net.samiayoub.school.dto.requets;

public record StudentCourseDetailsRequest(
    Long studentId,
    Long courseId
) { }
