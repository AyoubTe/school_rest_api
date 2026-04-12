package net.samiayoub.school.dto.responses;

public record CourseResponse(
        Long id,
        String code,
        String name
        // TeacherResponse teacher,
        // List<StudentResponse> students
) { }
