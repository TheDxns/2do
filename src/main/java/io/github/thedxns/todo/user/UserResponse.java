package io.github.thedxns.todo.user;

public class UserResponse {
    private String username;
    private String name;

    public UserResponse(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public static UserResponse from(UserDto user) {
        return new UserResponse(user.getUsername(), user.getName());
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
