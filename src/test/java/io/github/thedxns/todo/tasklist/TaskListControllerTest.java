package io.github.thedxns.todo.tasklist;

import io.github.thedxns.todo.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class TaskListControllerTest {

    @Mock
    TaskListService taskListService;

    @Mock
    UserService userService;

    @InjectMocks
    TaskListController taskListController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private TaskListRequest prepareListRequest() {
        return new TaskListRequest("Test", 15L);
    }

    private TaskListDto prepareTestTaskList() {
        return new TaskListDto(1L, "Test", 2L, Collections.emptyList());
    }

    private UserDto prepareTestUser() {
        final List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UserTestBuilder().id(1L).username("jdoe").firstName("John").surname("Doe").email("jdoe@mail.com").roles(roles).build();
    }

    @Test
    public void updateTaskList_shouldReturn200ResponseWhenTaskListCouldBeUpdated() {
        // Given
        given(taskListService.existsById(any())).willReturn(true);
        given(taskListService.updateTaskList(any(), any())).willReturn(true);

        // When
        // Then
        assertThat(taskListController.updateTaskList(1L, prepareListRequest())).isEqualTo(ResponseEntity.noContent().build());
    }

    @Test
    public void updateTaskList_shouldReturn404ResponseWhenNoTaskListFound() {
        // Given
        given(taskListService.existsById(anyLong())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.updateTaskList(1L, prepareListRequest())).isEqualTo(ResponseEntity.notFound().build());
    }

    @Test
    public void updateTaskList_shouldReturn500ResponseWhenTaskListCouldNotBeUpdated() {
        // Given
        given(taskListService.existsById(any())).willReturn(true);
        given(taskListService.updateTaskList(any(), any())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.updateTaskList(1L, prepareListRequest())).isEqualTo(ResponseEntity.internalServerError().build());
    }

    @Test
    public void deleteTaskList_shouldReturn200ResponseWhenTaskListCouldBeDeleted() {
        // Given
        given(taskListService.existsById(anyLong())).willReturn(true);
        given(taskListService.deleteTaskList(anyLong())).willReturn(true);

        // When
        // Then
        assertThat(taskListController.deleteTaskList(1L)).isEqualTo(ResponseEntity.ok().build());
    }

    @Test
    public void deleteTaskList_shouldReturn404ResponseWhenNoTaskListFound() {
        // Given
        given(taskListService.existsById(anyLong())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.deleteTaskList(1L)).isEqualTo(ResponseEntity.notFound().build());
    }

    @Test
    public void deleteTaskList_shouldReturn500ResponseWhenTaskListCouldNotBeDeleted() {
        // Given
        given(taskListService.existsById(any())).willReturn(true);
        given(taskListService.deleteTaskList(any())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.deleteTaskList(1L)).isEqualTo(ResponseEntity.internalServerError().build());
    }

    @Test
    public void getTaskListById_shouldReturn200ResponseWhenTaskListWasFound() {
        // Given
        final TaskListDto testTaskList = prepareTestTaskList();
        given(taskListService.existsById(any())).willReturn(true);
        given(taskListService.getTaskList(any())).willReturn(testTaskList);

        // When
        // Then
        assertThat(taskListController.getTaskListById(1L)).isEqualTo(ResponseEntity.ok(testTaskList));
    }

    @Test
    public void getTaskListById_shouldReturn404ResponseWhenNoTaskListFound() {
        // Given
        given(taskListService.existsById(any())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.getTaskListById(1L)).isEqualTo(ResponseEntity.notFound().build());
    }

    @Test
    public void saveTaskList_shouldReturn200ResponseWhenTaskListWasSaved() {
        // Given
        given(taskListService.saveTaskList(any())).willReturn(true);

        // When
        // Then
        assertThat(taskListController.saveTaskList(prepareListRequest())).isEqualTo(ResponseEntity.ok().build());
    }

    @Test
    public void saveTaskList_shouldReturn500ResponseWhenTaskListCouldNotBeSaved() {
        // Given
        given(taskListService.saveTaskList(any())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.saveTaskList(prepareListRequest())).isEqualTo(ResponseEntity.internalServerError().build());
    }

    @Test
    public void grantAccessToUser_shouldReturn200ResponseWhenAccessToTaskListCouldBeSet() {
        // Given
        given(userService.getUserById(anyLong())).willReturn(prepareTestUser());
        given(taskListService.existsById(anyLong())).willReturn(true);
        given(taskListService.grantAccessToUser(anyLong(), anyLong())).willReturn(true);

        // When
        // Then
        assertThat(taskListController.grantAccessToUser(1L, "John")).isEqualTo(ResponseEntity.noContent().build());
    }

    @Test
    public void grantAccessToUser_shouldReturn404ResponseWhenNoTaskListFound() {
        // Given
        given(userService.getUserById(anyLong())).willReturn(prepareTestUser());
        given(taskListService.existsById(anyLong())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.grantAccessToUser(1L, "John")).isEqualTo(ResponseEntity.notFound().build());
    }

    @Test
    public void grantAccessToUser_shouldReturn500ResponseWhenAccessToTaskListCouldNotBeSet() {
        // Given
        given(userService.getUserById(anyLong())).willReturn(prepareTestUser());
        given(taskListService.existsById(anyLong())).willReturn(true);
        given(taskListService.grantAccessToUser(anyLong(), anyLong())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.grantAccessToUser(1L, "John")).isEqualTo(ResponseEntity.internalServerError().build());
    }

    @Test
    public void removeAccessToUser_shouldReturn200ResponseWhenAccessToTaskListCouldBeRemoved() {
        // Given
        given(userService.getUserById(anyLong())).willReturn(prepareTestUser());
        given(taskListService.existsById(anyLong())).willReturn(true);
        given(taskListService.removeAccessOfUser(anyLong(), anyLong())).willReturn(true);

        // When
        // Then
        assertThat(taskListController.removeAccessOfUser(1L, "John")).isEqualTo(ResponseEntity.noContent().build());
    }

    @Test
    public void removeAccessToUser_shouldReturn404ResponseWhenNoTaskListFound() {
        // Given
        given(userService.getUserById(anyLong())).willReturn(prepareTestUser());
        given(taskListService.existsById(anyLong())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.removeAccessOfUser(1L, "John")).isEqualTo(ResponseEntity.notFound().build());
    }

    @Test
    public void removeAccessToUser_shouldReturn500ResponseWhenAccessToTaskListCouldNotBeRemoved() {
        // Given
        given(userService.getUserById(anyLong())).willReturn(prepareTestUser());
        given(taskListService.existsById(anyLong())).willReturn(true);
        given(taskListService.removeAccessOfUser(anyLong(), anyLong())).willReturn(false);

        // When
        // Then
        assertThat(taskListController.removeAccessOfUser(1L, "John")).isEqualTo(ResponseEntity.internalServerError().build());
    }

    @Test
    public void getPermittedUsers_shouldReturn200ResponseAndUsernameListWhenPermittedUsersExist() {
        // Given
        final List<UserResponse> permittedUsers = List.of(new UserResponse(1L, "jdoe", "John", "Doe", "jdoemail.com"));
        given(taskListService.existsById(anyLong())).willReturn(true);
        given(taskListService.getPermittedUsers(any())).willReturn(permittedUsers);

        // When
        final ResponseEntity<?> response = taskListController.getPermittedUsers(1L);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(permittedUsers);
    }

    @Test
    public void getPermittedUsers_shouldReturn200ResponseAndEmptyListWhenNoPermittedUsersFound() {
        // Given
        final List<UserResponse> userResponses = new ArrayList<>();
        given(taskListService.existsById(anyLong())).willReturn(true);
        given(taskListService.getPermittedUsers(any())).willReturn(userResponses);

        // When
        final ResponseEntity<?> response = taskListController.getPermittedUsers(1L);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.emptyList());
    }
}
