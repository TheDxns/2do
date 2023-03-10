package io.github.thedxns.todo.user;

import org.json.JSONArray;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class KeycloakApiService {

    private KeycloakApiClient keycloakApiClient;

    public KeycloakApiService(KeycloakApiClient keycloakApiClient) {
        this.keycloakApiClient = keycloakApiClient;
    }

    public List<KeycloakUserResponse> getAllUsers() {
        final ResponseEntity<?> response = keycloakApiClient.makeCall("/admin/realms/Todo/users", HttpMethod.GET, List.class);
        return (List<KeycloakUserResponse>) response.getBody();
    }

    public List<String> getAllUsersUsernames() {
        final ResponseEntity<?> response = keycloakApiClient.makeCall("/admin/realms/Todo/users", HttpMethod.GET, List.class);
        if (response.getBody() == null) {
            throw new RuntimeException("User not found");
        }
        final JSONArray allUserArray = new JSONArray(response.getBody());
        return getValuesFromJSONArrayByKey(allUserArray, "username");
    }

    public UserDto getUser(String id) {
        final ResponseEntity<?> response = keycloakApiClient.makeCall("/admin/realms/Todo/users/" + id, HttpMethod.GET,
                KeycloakUserResponse.class);
        final KeycloakUserResponse user = (KeycloakUserResponse) response.getBody();

        if (user != null) {
            return new UserDto(id, user.getFirstName() + " " + user.getLastName());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public String getUserIdByUsername(String key) {
        final ResponseEntity<?> response = keycloakApiClient.makeCall("/admin/realms/Todo/users", HttpMethod.GET, List.class);
        if (response.getBody() == null) {
            throw new RuntimeException("User not found");
        }
        final JSONArray userArray = new JSONArray(response.getBody());
        final Map<String, String> users = convertJSONArrayToStringMap(userArray, "id", "username");

        return users.get(key);
    }

    public String getUsername(String key) {
        final ResponseEntity<?> response = keycloakApiClient.makeCall("/admin/realms/Todo/users", HttpMethod.GET, List.class);
        if (response.getBody() == null) {
            throw new RuntimeException("User not found");
        }
        final JSONArray userArray = new JSONArray(response.getBody());
        final Map<String, String> users = convertJSONArrayToStringMap(userArray, "username", "id");

        return users.get(key);
    }

    private Map<String, String> convertJSONArrayToStringMap(final JSONArray jsonArray, final String key, final String value) {
        final Map<String, String> map = new TreeMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            map.put(jsonArray.getJSONObject(i).getString(key), jsonArray.getJSONObject(i).getString(value));
        }
        return map;
    }

    private List<String> getValuesFromJSONArrayByKey(final JSONArray jsonArray, final String key) {
        final List<String> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getJSONObject(i).getString("username"));
        }
        return list;
    }
}
