package io.github.thedxns.todo.user;

public class UserTestBuilder {

    private String keycloakId;
    private String name;

    public UserTestBuilder keycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
        return this;
    }

    public UserTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserDto build() {
        return new UserDto(keycloakId, name);
    }
}