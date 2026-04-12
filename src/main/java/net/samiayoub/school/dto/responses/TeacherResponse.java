package net.samiayoub.school.dto.responses;

public record TeacherResponse(
        Long id,
        String username,
        String firstname,
        String lastname,
        String email,
        // String password,
        String discipline
        // SchoolResponse school,
        // List<CourseResponse> courses
) { }
