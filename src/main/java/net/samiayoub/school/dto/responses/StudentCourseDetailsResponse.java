package net.samiayoub.school.dto.responses;

public record StudentCourseDetailsResponse(
        Long id,
        Long studentId,
        Long courseId
        // List<CourseResponse> courses,
        // GradeDetailsResponse grades
) { }
