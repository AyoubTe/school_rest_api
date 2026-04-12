package net.samiayoub.school.dto.responses;


public record LoginResponse (
        String token,
        String message,
        String role
) { }
