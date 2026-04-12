package net.samiayoub.school.dto.requets;

public record AdminRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        String mission
) { }
