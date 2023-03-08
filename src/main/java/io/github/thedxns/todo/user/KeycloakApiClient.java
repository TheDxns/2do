package io.github.thedxns.todo.user;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class KeycloakApiClient {

    private final String keycloakServerUrl;
    private final String keycloakRealm;

    public KeycloakApiClient(@Value("${keycloak.auth-server-url}") String keycloakServerUrl, @Value("${keycloak.realm}") String keycloakRealm) {
        this.keycloakServerUrl = keycloakServerUrl;
        this.keycloakRealm = keycloakRealm;
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

    public ResponseEntity<?> makeCall(String endpoint, HttpMethod httpMethod, Class entityClass) {
        final Keycloak keycloak = initKeycloak();

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());
        final HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(URI.create(keycloakServerUrl + endpoint), httpMethod, request, entityClass);
    }

}
