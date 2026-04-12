package net.samiayoub.school.dto.requets;

public record StudentRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password
) {
}
