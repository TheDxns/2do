package io.github.thedxns.todo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getUser_shouldReturn200WhenUserWhenFound() {
        // Given
        final UserDto user = new UserDto(1L, "jdoe", "John", "Doe", "jdoe@mail.com", new ArrayList<>());
        given(userService.getUserById(anyLong())).willReturn(user);

        // When
        // Then
        assertThat(userController.getUser(1L)).isEqualTo(ResponseEntity.ok(user));
    }

    @Test
    public void getUser_shouldReturn404WhenUserNotWhenFound() {
        // Given
        given(userService.getUserById(anyLong())).willReturn(null);

        // When
        // Then
        assertThat(userController.getUser(1L)).isEqualTo(ResponseEntity.notFound().build());
    }

    @Test
    public void getUserIdByUsername_shouldReturn200WhenUserWhenFound() {
        // Given
        given(userService.getUserId("test")).willReturn(1L);

        // When
        // Then
        assertThat(userController.getUserIdByUsername("test")).isEqualTo(ResponseEntity.ok(1L));
    }

    @Test
    public void getUserIdByUsername_shouldReturn404WhenUserNotWhenFound() {
        // Given
        given(userService.getUserId(any())).willReturn(null);

        // When
        // Then
        assertThat(userController.getUserIdByUsername("test")).isEqualTo(ResponseEntity.notFound().build());
    }
}
