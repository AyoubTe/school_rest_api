package net.samiayoub.school.dto.responses;

public record AdminResponse (
        Long id,
        String username,
        String firstname,
        String lastname,
        String email,
        // String password,
        String mission
) {
}
