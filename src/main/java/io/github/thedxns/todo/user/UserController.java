package io.github.thedxns.todo.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONArray;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final String keycloakServerUrl;
    private final String keycloakRealm;

	public UserController(@Value("${keycloak.auth-server-url}") String keycloakServerUrl, @Value("${keycloak.realm}") String keycloakRealm) {
		this.keycloakServerUrl = keycloakServerUrl;
        this.keycloakRealm = keycloakRealm;
	}

	@GetMapping
	private Object getAllUsers() {
		final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(keycloakServerUrl)
            .grantType(OAuth2Constants.PASSWORD)
            .realm(keycloakRealm)
            .clientId("todo-client")
            .username("serviceaccount")
            .password("serviceaccount")
            .resteasyClient(
                new ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            ).build();

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());
        final HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(URI.create(keycloakServerUrl + "/admin/realms/Todo/users"), HttpMethod.GET, request, List.class).getBody();			
	}

    @GetMapping("/username")
	private List<String> getAllUsersUsernames() {
		final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(keycloakServerUrl)
            .grantType(OAuth2Constants.PASSWORD)
            .realm(keycloakRealm)
            .clientId("todo-client")
            .username("serviceaccount")
            .password("serviceaccount")
            .resteasyClient(
                new ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            ).build();

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());
        final HttpEntity<String> request = new HttpEntity<>(headers);
        final JSONArray userArray = new JSONArray(restTemplate.exchange(URI.create(keycloakServerUrl + "/admin/realms/Todo/users"),
            HttpMethod.GET, request, List.class).getBody());
        final List<String> users = new ArrayList<>();
        String username;
        for (int i = 0; i < userArray.length(); i++) {
            username = userArray.getJSONObject(i).getString("username");
            users.add(username);
        }
        return users;			
	}

    @GetMapping("/{id}")
	private Object getUser(@PathVariable String id) {
	final Keycloak keycloak = KeycloakBuilder.builder()
        .serverUrl("http://localhost:8180/auth")
        .grantType(OAuth2Constants.PASSWORD)
        .realm("Todo")
        .clientId("todo-client")
        .username("serviceaccount")
        .password("serviceaccount")
        .resteasyClient(
            new ResteasyClientBuilder()
                .connectionPoolSize(10).build()
        ).build();

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());
        final HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(URI.create(keycloakServerUrl + "/admin/realms/Todo/users/" + id), HttpMethod.GET, request, Object.class).getBody();			
	}

    @GetMapping("/id/{key}")
	public String getUserByUsername(@PathVariable String key) {
		final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(keycloakServerUrl)
            .grantType(OAuth2Constants.PASSWORD)
            .realm(keycloakRealm)
            .clientId("todo-client")
            .username("serviceaccount")
            .password("serviceaccount")
            .resteasyClient(
                new ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            ).build();

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());
        final HttpEntity<String> request = new HttpEntity<>(headers);
        final JSONArray userArray = new JSONArray(restTemplate.exchange(URI.create(keycloakServerUrl + "/admin/realms/Todo/users"),
            HttpMethod.GET, request, List.class).getBody());
        final Map<String, String> users = new TreeMap<>();
        String username;
        String id;
        for (int i = 0; i < userArray.length(); i++) {
            username = userArray.getJSONObject(i).getString("username");
            id = userArray.getJSONObject(i).getString("id");
            users.put(username, id);
        }
        return users.get(key);	
	}

    @GetMapping("/username/{key}")
    public String getUsername(@PathVariable String key) {
		final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(keycloakServerUrl)
            .grantType(OAuth2Constants.PASSWORD)
            .realm(keycloakRealm)
            .clientId("todo-client")
            .username("serviceaccount")
            .password("serviceaccount")
            .resteasyClient(
                new ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            ).build();

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + keycloak.tokenManager().getAccessToken().getToken());
        final HttpEntity<String> request = new HttpEntity<>(headers);
        final JSONArray userArray = new JSONArray(restTemplate.exchange(URI.create(keycloakServerUrl + "/admin/realms/Todo/users"),
            HttpMethod.GET, request, List.class).getBody());
        final Map<String, String> users = new TreeMap<>();
        String username;
        String id;
        for (int i = 0; i < userArray.length(); i++) {
            username = userArray.getJSONObject(i).getString("username");
            id = userArray.getJSONObject(i).getString("id");
            users.put(id, username);
        }
        return users.get(key);	
	}

}