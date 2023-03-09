package io.github.thedxns.todo.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final KeycloakApiService keycloakApiService;

    public UserController(KeycloakApiService keycloakApiService) {
        this.keycloakApiService = keycloakApiService;
    }

    @GetMapping
	private ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(keycloakApiService.getAllUsers());
	}

    @GetMapping("/username")
	private ResponseEntity<?> getAllUsersUsernames() {
        return ResponseEntity.ok(keycloakApiService.getAllUsersUsernames());
	}

    @GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
        final UserDto user = keycloakApiService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/id/{key}")
	public ResponseEntity<?> getUserIdByUsername(@PathVariable String key) {
        final String userId = keycloakApiService.getUserIdByUsername(key);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userId);
        }
	}

    @GetMapping("/username/{key}")
    public ResponseEntity<?> getUsername(@PathVariable String key) {
        final String username = keycloakApiService.getUsername(key);
        if (username == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(username);
        }
	}



}