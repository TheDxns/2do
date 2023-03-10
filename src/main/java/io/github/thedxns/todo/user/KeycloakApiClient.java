package io.github.thedxns.todo.user;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class KeycloakApiClient {

    enum KeycloakEndpoints {
        GET_USERS("/admin/realms/Todo/users/", HttpMethod.GET);

        private final String endpoint;
        private final HttpMethod httpMethod;

        KeycloakEndpoints(String endpoint, HttpMethod httpMethod) {
            this.endpoint = endpoint;
            this.httpMethod = httpMethod;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }
    }

    private final String keycloakServerUrl;
    private final String keycloakRealm;
    private final RestTemplate restTemplate;
    private final Keycloak keycloak;

    public KeycloakApiClient(@Value("${keycloak.auth-server-url}") String keycloakServerUrl, @Value("${keycloak.realm}") String keycloakRealm) {
        this.keycloakServerUrl = keycloakServerUrl;
        this.keycloakRealm = keycloakRealm;
        this.restTemplate = new RestTemplate();
        this.keycloak = initKeycloak();
    }

    private Keycloak initKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm(keycloakRealm)
                .clientId("todo-client")
                .username("serviceaccount")
                .password("serviceaccount")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }

    public List<KeycloakUserResponse> getAllUsers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());

        final HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<KeycloakUserResponse[]> response = restTemplate.exchange(
                keycloakServerUrl + KeycloakEndpoints.GET_USERS.getEndpoint(),
                KeycloakEndpoints.GET_USERS.getHttpMethod(), entity, KeycloakUserResponse[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            KeycloakUserResponse[] users = response.getBody();
            return Arrays.asList(users);
        } else {
            throw new RuntimeException("Failed to get all users");
        }
    }

    public KeycloakUserResponse getUserById(String userId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());

        final HttpEntity<Void> entity = new HttpEntity<>(headers);

        final ResponseEntity<KeycloakUserResponse> response = restTemplate.exchange(
                keycloakServerUrl + KeycloakEndpoints.GET_USERS.getEndpoint() + userId,
                KeycloakEndpoints.GET_USERS.getHttpMethod(), entity, KeycloakUserResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to get user with ID: " + userId);
        }
    }

}
