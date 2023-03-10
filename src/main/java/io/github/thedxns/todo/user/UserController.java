package io.github.thedxns.todo.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
	private ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
	}

    @GetMapping("/username")
	private ResponseEntity<?> getAllUsersUsernames() {
        return ResponseEntity.ok(userService.getAllUsernames());
	}

    @GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
        final UserDto user = userService.getUserById(new KeycloakId(id));
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/id/{key}")
	public ResponseEntity<?> getUserIdByUsername(@PathVariable String key) {
        final KeycloakId userId = userService.getUserId(key);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userId);
        }
	}

    @GetMapping("/username/{key}")
    public ResponseEntity<?> getUsername(@PathVariable String key) {
        final String username = userService.getUsername(new KeycloakId(key));
        if (username == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(username);
        }
	}
}