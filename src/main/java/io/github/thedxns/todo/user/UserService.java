package io.github.thedxns.todo.user;

import io.github.thedxns.todo.auth.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::from).collect(Collectors.toList());
    }

    public UserDto getUserById(final long id) {
        final Optional<User> user = userRepository.findById(id);
        return user.map(UserDto::from).orElse(null);
    }

    public List<String> getAllUsernames() {
        return getAllUsers().stream().map(UserDto::getUsername).collect(Collectors.toList());
    }

    public Long getUserId(String username) {
        final Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getId).orElse(null);
    }

    public User getEntityByUsername(String username) {
        final Optional<User> user = userRepository.findByUsernameWithRoles(username);
        return user.orElse(null);
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean saveUser(UserRequest userRequest) {
        final User user = new User(null, userRequest.getUsername(), authService.encodePassword(userRequest.getPassword()),
                userRequest.getFirstName(), userRequest.getSurname(), userRequest.getEmail(),
                List.of("USER"));
        userRepository.save(user);
        return true;
    }
}
