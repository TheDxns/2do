package io.github.thedxns.todo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class KeycloakApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Keycloak keycloak;

    @Mock
    private KeycloakApiClient keycloakApiClient;

    private KeycloakUserResponse[] userResponses;
    private TokenManager tokenManager;
    private AccessTokenResponse accessTokenResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(keycloakApiClient, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(keycloakApiClient, "keycloak", keycloak);
        this.userResponses = new KeycloakUserResponse[]{new KeycloakUserResponse()};
        this.tokenManager = mock(TokenManager.class);
        this.accessTokenResponse = mock(AccessTokenResponse.class);
    }

    @Test
    public void getAllUsers_shouldReturnUsersIfResponseStatus200() {
        // Given
        given(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
                .willReturn(new ResponseEntity<>(userResponses, HttpStatus.OK));
        given(keycloak.tokenManager()).willReturn(tokenManager);
        given(tokenManager.getAccessToken()).willReturn(accessTokenResponse);
        given(accessTokenResponse.getToken()).willReturn("testToken");
        given(keycloakApiClient.getAllUsers()).willCallRealMethod();
        // When
        final List<KeycloakUserResponse> users = keycloakApiClient.getAllUsers();

        // Then
        assertEquals(Arrays.asList(userResponses), users);
    }

    @Test
    public void getAllUsers_shouldThrowExceptionWhenCouldNotGetUsers() {
        // Given
        given(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
                .willReturn(new ResponseEntity<>(userResponses, HttpStatus.INTERNAL_SERVER_ERROR));
        given(keycloak.tokenManager()).willReturn(tokenManager);
        given(tokenManager.getAccessToken()).willReturn(accessTokenResponse);
        given(accessTokenResponse.getToken()).willReturn("testToken");
        given(keycloakApiClient.getAllUsers()).willCallRealMethod();

        // When
        // Then
        assertThrows(RuntimeException.class, () -> keycloakApiClient.getAllUsers());
    }

    @Test
    public void getUserById_shouldReturnUserIfResponseStatus200() {
        final KeycloakUserResponse userResponse = new KeycloakUserResponse();
        given(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
                .willReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));
        given(keycloak.tokenManager()).willReturn(tokenManager);
        given(tokenManager.getAccessToken()).willReturn(accessTokenResponse);
        given(accessTokenResponse.getToken()).willReturn("testToken");
        given(keycloakApiClient.getUserById("userId")).willCallRealMethod();
        // When
        final KeycloakUserResponse user = keycloakApiClient.getUserById("userId");

        // Verify results
        assertEquals(userResponse, user);
    }

    @Test
    public void getUserById_shouldThrowExceptionWhenCouldNotGetUser()  {
        final KeycloakUserResponse userResponse = new KeycloakUserResponse();
        given(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), (Class<Object>) any()))
                .willReturn(new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR));
        given(keycloak.tokenManager()).willReturn(tokenManager);
        given(tokenManager.getAccessToken()).willReturn(accessTokenResponse);
        given(accessTokenResponse.getToken()).willReturn("testToken");
        given(keycloakApiClient.getUserById("userId")).willCallRealMethod();
        // When
        assertThrows(RuntimeException.class, () -> keycloakApiClient.getUserById("userId"));
    }
}
