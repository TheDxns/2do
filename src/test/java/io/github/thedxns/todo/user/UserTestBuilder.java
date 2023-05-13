package io.github.thedxns.todo.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserTestBuilder {

    private Long id;
    private String username;
    private String firstName;
    private String surname;
    private String email;
    private List<SimpleGrantedAuthority> roles;

    public UserTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserTestBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserTestBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserTestBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserTestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserTestBuilder roles(List<SimpleGrantedAuthority> roles) {
        this.roles = roles;
        return this;
    }

    public UserDto build() {
        return new UserDto(id, username, firstName, surname, email, roles);
    }
}
