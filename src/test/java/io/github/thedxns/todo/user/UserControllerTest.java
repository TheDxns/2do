package io.github.thedxns.todo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserControllerTest {

//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void getUser_shouldReturn200WhenUserWhenFound() {
//        // Given
//        final UserDto user = new UserDto("test", "John Doe", "jdoe");
//        given(userService.getUserById(any())).willReturn(user);
//
//        // When
//        // Then
//        assertThat(userController.getUser("test")).isEqualTo(ResponseEntity.ok(user));
//    }
//
//    @Test
//    public void getUser_shouldReturn404WhenUserNotWhenFound() {
//        // Given
//        given(userService.getUserById(any())).willReturn(null);
//
//        // When
//        // Then
//        assertThat(userController.getUser("test")).isEqualTo(ResponseEntity.notFound().build());
//    }
//
//    @Test
//    public void getUserIdByUsername_shouldReturn200WhenUserWhenFound() {
//        // Given
//        final KeycloakId userId = new KeycloakId("userId");
//        given(userService.getUserId(any())).willReturn(userId);
//
//        // When
//        // Then
//        assertThat(userController.getUserIdByUsername("test")).isEqualTo(ResponseEntity.ok(userId));
//    }
//
//    @Test
//    public void getUserIdByUsername_shouldReturn404WhenUserNotWhenFound() {
//        // Given
//        given(userService.getUserId(any())).willReturn(null);
//
//        // When
//        // Then
//        assertThat(userController.getUserIdByUsername("test")).isEqualTo(ResponseEntity.notFound().build());
//    }
//
//    @Test
//    public void getUsername_shouldReturn200WhenUserWhenFound() {
//        // Given
//        final String userId = "userId";
//        given(userService.getUsername(any())).willReturn(userId);
//
//        // When
//        // Then
//        assertThat(userController.getUsername("test")).isEqualTo(ResponseEntity.ok(userId));
//    }
//
//    @Test
//    public void getUsername_shouldReturn404WhenUserNotWhenFound() {
//        // Given
//        given(userService.getUserById(any())).willReturn(null);
//
//        // When
//        // Then
//        assertThat(userController.getUsername("test")).isEqualTo(ResponseEntity.notFound().build());
//    }
}
