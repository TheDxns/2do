package io.github.thedxns.todo.user;

public class UserResponse {

    private long id;
    private String username;
    private String firstName;
    private String surname;
    private String email;

    public UserResponse(long id, String username, String firstName, String surname, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
    }

    public static UserResponse from(UserDto user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getSurname(), user.getEmail());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
