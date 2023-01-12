package io.github.thedxns.todo.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserController userController;

    public UserService(UserController userController) {
        this.userController = userController;
    }

    public KeycloakId getUserId(final String username) {
        return new KeycloakId(userController.getUserByUsername(username));
    }

    public String getUsername(final KeycloakId userId) {
        return userController.getUsername(userId.getId());
    }
}
