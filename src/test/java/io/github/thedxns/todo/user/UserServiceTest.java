package io.github.thedxns.todo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {

//    @Mock
//    private KeycloakApiClient keycloakApiClient;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void getUserId_shouldReturnUserIdIfFoundUser() {
//        // Given
//        final KeycloakUserResponse userResponse = new KeycloakUserResponse();
//        userResponse.setUsername("username");
//        final List<KeycloakUserResponse> userResponses = Collections.singletonList(userResponse);
//        given(keycloakApiClient.getAllUsers()).willReturn(userResponses);
//
//        // When
//        // Then
//        assertThat(userService.getUserId("username")).isNotNull();
//    }
//
//    @Test
//    public void getUserId_shouldReturnExceptionIfNotFoundUser() {
//        // Given
//        final List<KeycloakUserResponse> userResponses = Collections.singletonList(new KeycloakUserResponse());
//        given(keycloakApiClient.getAllUsers()).willReturn(userResponses);
//
//        // When
//        // Then
//        assertThrows(RuntimeException.class, () -> userService.getUserId("username"));
//    }
}
