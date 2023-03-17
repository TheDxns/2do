package io.github.thedxns.todo.user;

public class UserResponse {

    private String id;
    private String username;
    private String name;

    public UserResponse(String id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public static UserResponse from(UserDto user) {
        return new UserResponse(user.getKeycloakId(), user.getUsername(), user.getName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
