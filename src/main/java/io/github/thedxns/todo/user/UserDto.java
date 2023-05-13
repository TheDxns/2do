package io.github.thedxns.todo.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String surname;
    private final String email;
    private final List<SimpleGrantedAuthority> roles;

    public UserDto(Long id, String username, String firstName, String surname, String email, List<SimpleGrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public List<SimpleGrantedAuthority> getRoles() {
        return roles;
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getSurname(), user.getEmail(),
                mapRoles(user.getRoles()));
    }

    private static List<SimpleGrantedAuthority> mapRoles(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
