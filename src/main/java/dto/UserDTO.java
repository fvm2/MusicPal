package dto;

public record UserDTO(
        String name,
        String surname,
        String email,
        String country,
        String password
) {}
