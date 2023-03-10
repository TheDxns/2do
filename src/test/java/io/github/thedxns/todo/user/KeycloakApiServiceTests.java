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

public class KeycloakApiServiceTests {

    @Mock
    private KeycloakApiClient keycloakApiClient;

    @InjectMocks
    private KeycloakApiService keycloakApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getUser_should() {
        // Given
        final KeycloakUserResponse keycloakUserResponse = new KeycloakUserResponse();
        final ResponseEntity<?> response = ResponseEntity.ok(keycloakUserResponse);
        given(keycloakApiClient.makeCall(any(), any(), any())).willReturn(response);

        // When
        // Then
        assertThat(keycloakApiService.getUser("userId")).isEqualTo(response.getBody());
    }
}
