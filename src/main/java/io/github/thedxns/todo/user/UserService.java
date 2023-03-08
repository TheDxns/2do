package io.github.thedxns.todo.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final KeycloakApiService keycloakApiService;

    public UserService(KeycloakApiService keycloakApiService) {
        this.keycloakApiService = keycloakApiService;
    }

    public UserDto getUserById(final KeycloakId id) {
        return keycloakApiService.getUser(id.getId());
    }

    public KeycloakId getUserId(final String username) {
        return new KeycloakId(keycloakApiService.getUserIdByUsername(username));
    }

    public String getUsername(final KeycloakId userId) {
        return keycloakApiService.getUsername(userId.getId());
    }
}
