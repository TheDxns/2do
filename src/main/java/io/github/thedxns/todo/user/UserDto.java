package io.github.thedxns.todo.user;

public class UserDto {
    private final String keycloakId;
    private final String name;
    private final String username;

    public UserDto(String keycloakId, String name, String username) {
        this.keycloakId = keycloakId;
        this.name = name;
        this.username = username;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public static UserDto from(KeycloakUserResponse userResponse) {
        return new UserDto(userResponse.getId(), userResponse.getFirstName() + " " + userResponse.getLastName(),
                userResponse.getUsername());
    }
}
