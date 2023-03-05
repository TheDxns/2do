package io.github.thedxns.todo.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.tasklist.TaskListService;
import io.github.thedxns.todo.user.KeycloakId;
import io.github.thedxns.todo.user.UserDto;
import io.github.thedxns.todo.user.UserService;
import io.github.thedxns.todo.user.UserTestBuilder;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TaskServiceTests {

    @Mock
    private TaskListService taskListServiceMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private TaskRepository taskRepositoryMock;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.taskService = new TaskService(taskListServiceMock, userServiceMock, taskRepositoryMock);
    }

    private TaskDto prepareTestTask() {
        final TaskListDto taskList = new TaskListDto(1L, "Test", new KeycloakId("123"), Collections.emptyList());
        return new TaskTestBuilder().id(1L).taskList(taskList).title("Test").description("Test task")
                .priority(TaskPriority.MINOR).status(TaskStatus.WAITING).creator(prepareTestUser()).deadline(LocalDateTime.now())
                .createdOn(LocalDateTime.now()).updatedOn(null).build();
    }

    private UserDto prepareTestUser() {
        return new UserTestBuilder().name("John Doe").keycloakId("123").build();
    }

    public LocalDateTime getTestDate() {
        return LocalDateTime.parse("2019-05-08T11:33:08.863");
    }

    public void setUpTestTask() {
        Task task1 = new Task();
        task1.setTitle("A new test task");
        task1.setContent("<h1>This task was created for unit testing purpose.</h1>");
        task1.setCreatorId("user1234");
        task1.setPriority(TaskPriority.MAJOR);
        task1.setStatus(TaskStatus.WAITING);
        task1.setCreatedOn(getTestDate());
        doReturn(task1).when(taskRepositoryMock).getById((long) 1);
    }

    public static String generateRandomString(int length) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
          +"lmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder stringBuilder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
            stringBuilder.append(chars.charAt(rnd.nextInt(chars.length())));
        }
		return stringBuilder.toString();
	}

    @Test
    public void shouldReturnAllTasksFromRepository() {
        // Given
        final Task firstTask = new Task(prepareTestTask());
        final Task secondTask = new Task(prepareTestTask());
        final Task thirdTask = new Task(prepareTestTask());
        given(taskRepositoryMock.findAll()).willReturn(Arrays.asList(firstTask, secondTask, thirdTask));

        // When
        // Then
        assertThat(taskService.getAllTasks()).hasSize(3);
    }

    @Test
    @Disabled
    //TODO: Need to finish test
    public void shouldSwitchTaskPriorityIfTaskExistsAndPriorityIsMinor() {
        // Given
        final Task task = new Task(prepareTestTask());
        task.setPriority(TaskPriority.MINOR);

        // When
        // Then
    }

    @Test
    @Disabled
    //TODO: Need to finish test
    public void shouldSwitchTaskPriorityIfTaskExistsAndPriorityIsMajor() {
        // Given
        final Task task = new Task(prepareTestTask());
        task.setPriority(TaskPriority.MINOR);

        // When
        // Then
    }

    @Test
    public void switchPriorityWhenTaskNotExistsShouldThrowException() {
        // Given
        given(taskRepositoryMock.getById(any())).willReturn(null);

        // When
        // Then
        assertThatThrownBy(() -> taskService.switchPriority(1L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @Disabled
    //TODO: Need to finish test
    public void shouldFinishTaskWhenTaskExists() {
        // Given
        given(taskRepositoryMock.getById(any())).willReturn(null);

        // When
        // Then
    }

    @Test
    public void finishTaskWhenTaskNotExistsShouldThrowException() {
        // Given
        given(taskRepositoryMock.getById(any())).willReturn(null);

        // When
        // Then
        assertThatThrownBy(() -> taskService.finishTask(1L)).isInstanceOf(RuntimeException.class);
    }
}
