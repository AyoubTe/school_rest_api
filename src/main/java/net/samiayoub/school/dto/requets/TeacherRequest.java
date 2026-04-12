package net.samiayoub.school.dto.requets;

public record TeacherRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        String discipline
) { }
