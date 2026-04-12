package net.samiayoub.school.dto.responses;

public record StudentResponse(
        Long id,
        String username,
        String firstname,
        String lastname,
        String email
        // String password
) { }
