package io.github.thedxns.todo.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final KeycloakApiClient keycloakApiClient;

    public UserService(KeycloakApiClient keycloakApiClient) {
        this.keycloakApiClient = keycloakApiClient;
    }

    public List<UserDto> getAllUsers() {
        return keycloakApiClient.getAllUsers().stream().map(UserDto::from).collect(Collectors.toList());
    }

    public List<String> getAllUsernames() {
        return keycloakApiClient.getAllUsers().stream().map(KeycloakUserResponse::getUsername).collect(Collectors.toList());
    }

    public UserDto getUserById(final KeycloakId id) {
        final KeycloakUserResponse userResponse = keycloakApiClient.getUserById(id.getId());
        return UserDto.from(userResponse);
    }

    public KeycloakId getUserId(final String username) {
        final List<KeycloakUserResponse> userResponses = keycloakApiClient.getAllUsers();
        final Optional<KeycloakUserResponse> userId = userResponses.stream().filter(
                v -> Objects.equals(v.getUsername(), username)).findFirst();
        if (userId.isPresent()) {
            return new KeycloakId(userId.get().getId());
        } else {
            throw new RuntimeException("User not exist");
        }
    }

    public String getUsername(final KeycloakId userId) {
        final KeycloakUserResponse userResponse = keycloakApiClient.getUserById(userId.getId());
        return userResponse.getUsername();
    }
}
