package io.github.thedxns.todo.user;

public class UserDto {
    private final String keycloakId;
    private final String name;

    public UserDto(String keycloakId, String name) {
        this.keycloakId = keycloakId;
        this.name = name;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public String getName() {
        return name;
    }

    public static UserDto from(KeycloakUserResponse userResponse) {
        return new UserDto(userResponse.getId(), userResponse.getFirstName() + " " + userResponse.getLastName());
    }
}
