package io.github.thedxns.todo.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserDto {
    private final String name;
    private final String username;

    private final List<SimpleGrantedAuthority> roles;

    public UserDto(String name, String username, List<SimpleGrantedAuthority> roles) {
        this.name = name;
        this.username = username;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public List<SimpleGrantedAuthority> getRoles() {
        return roles;
    }

    public static UserDto from(User user) {
        return new UserDto(userResponse.getId(), userResponse.getFirstName() + " " + userResponse.getLastName(),
                userResponse.getUsername());
    }
}
