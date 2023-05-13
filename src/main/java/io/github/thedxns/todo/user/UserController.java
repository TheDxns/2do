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
	public ResponseEntity<?> getUser(@PathVariable Long id) {
        final UserDto user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/id/{username}")
	public ResponseEntity<?> getUserIdByUsername(@PathVariable String username) {
        final Long userId = userService.getUserId(username);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userId);
        }
	}
}