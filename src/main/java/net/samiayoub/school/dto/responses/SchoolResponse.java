package net.samiayoub.school.dto.responses;

public record SchoolResponse(
    Long id,
    String name,
    String address
    // List<TeacherResponse> teachers
) { }
