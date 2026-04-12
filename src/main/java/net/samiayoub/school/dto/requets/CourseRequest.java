package net.samiayoub.school.dto.requets;

public record CourseRequest(
        String code,
        String name,
        TeacherRequest teacherRequest
) {
}
