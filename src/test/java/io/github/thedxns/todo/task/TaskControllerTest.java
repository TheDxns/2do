package io.github.thedxns.todo.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.user.KeycloakId;
import io.github.thedxns.todo.user.UserDto;
import io.github.thedxns.todo.user.UserTestBuilder;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;

public class TaskControllerTest {

	@Mock
	TaskService taskService;

	@InjectMocks
	TaskController taskController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private TaskRequest prepareTaskRequest() {
		return new TaskRequest("Test", "Description", TaskPriority.MINOR, TaskStatus.WAITING,
				"123", 1L, LocalDateTime.now());
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

	@Test
	public void updateTask_shouldReturn200ResponseWhenTaskCouldBeUpdated() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.updateTask(any(), any())).willReturn(true);

		// When
		// Then
		assertThat(taskController.updateTask(1L, prepareTaskRequest())).isEqualTo(ResponseEntity.noContent().build());
	}

	@Test
	public void updateTask_shouldReturn404ResponseWhenNoTaskFound() {
		// Given
		given(taskService.existsById(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.updateTask(1L, prepareTaskRequest())).isEqualTo(ResponseEntity.notFound().build());
	}

	@Test
	public void updateTask_shouldReturn500ResponseWhenTaskCouldNotBeUpdated() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.updateTask(any(), any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.updateTask(1L, prepareTaskRequest())).isEqualTo(ResponseEntity.internalServerError().build());
	}

	@Test
	public void deleteTask_shouldReturn200ResponseWhenTaskCouldBeDeleted() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.deleteTask(any())).willReturn(true);

		// When
		// Then
		assertThat(taskController.deleteTask(1L)).isEqualTo(ResponseEntity.ok().build());
	}

	@Test
	public void deleteTask_shouldReturn404ResponseWhenNoTaskFound() {
		// Given
		given(taskService.existsById(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.deleteTask(1L)).isEqualTo(ResponseEntity.notFound().build());
	}

	@Test
	public void deleteTask_shouldReturn500ResponseWhenTaskCouldNotBeDeleted() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.deleteTask(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.deleteTask(1L)).isEqualTo(ResponseEntity.internalServerError().build());
	}

	@Test
	public void finishTask_shouldReturn200ResponseWhenTaskWasFinished() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.finishTask(any())).willReturn(true);

		// When
		// Then
		assertThat(taskController.finishTask(1L)).isEqualTo(ResponseEntity.noContent().build());
	}

	@Test
	public void finishTask_shouldReturn404ResponseWhenNoTaskFound() {
		// Given
		given(taskService.existsById(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.finishTask(1L)).isEqualTo(ResponseEntity.notFound().build());
	}

	@Test
	public void finishTask_shouldReturn500ResponseWhenTaskCouldNotBeFinished() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.finishTask(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.finishTask(1L)).isEqualTo(ResponseEntity.internalServerError().build());
	}

	@Test
	public void prioritizeTask_shouldReturn200ResponseWhenTaskWasPrioritized() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.switchPriority(any())).willReturn(true);

		// When
		// Then
		assertThat(taskController.prioritizeTask(1L)).isEqualTo(ResponseEntity.noContent().build());
	}

	@Test
	public void prioritizeTask_shouldReturn404ResponseWhenNoTaskFound() {
		// Given
		given(taskService.existsById(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.prioritizeTask(1L)).isEqualTo(ResponseEntity.notFound().build());
	}

	@Test
	public void prioritizeTask_shouldReturn500ResponseWhenTaskCouldNotBePrioritized() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.switchPriority(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.prioritizeTask(1L)).isEqualTo(ResponseEntity.internalServerError().build());
	}

	@Test
	public void saveCustomTask_shouldReturn200ResponseWhenTaskWasSaved() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.saveCustomListTask(any(), any())).willReturn(true);

		// When
		// Then
		assertThat(taskController.saveCustomListTask(1L, prepareTaskRequest())).isEqualTo(ResponseEntity.ok().build());
	}

	@Test
	public void saveCustomTask_shouldReturn404ResponseWhenNoTaskFound() {
		// Given
		given(taskService.existsById(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.saveCustomListTask(1L, prepareTaskRequest())).isEqualTo(ResponseEntity.notFound().build());
	}

	@Test
	public void saveCustomTask_shouldReturn500ResponseWhenTaskCouldNotBeSaved() {
		// Given
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.saveCustomListTask(any(), any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.saveCustomListTask(1L, prepareTaskRequest())).isEqualTo(ResponseEntity.internalServerError().build());
	}

	@Test
	public void saveTask_shouldReturn200ResponseWhenTaskWasSaved() {
		// Given
		given(taskService.saveTask(any())).willReturn(true);

		// When
		// Then
		assertThat(taskController.saveTask(prepareTaskRequest())).isEqualTo(ResponseEntity.ok().build());
	}

	@Test
	public void saveTask_shouldReturn500ResponseWhenTaskCouldNotBeSaved() {
		// Given
		given(taskService.saveTask(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.saveTask(prepareTaskRequest())).isEqualTo(ResponseEntity.internalServerError().build());
	}

	@Test
	public void getTaskById_shouldReturn200ResponseWhenTaskWasFound() {
		// Given
		final TaskDto testTask = prepareTestTask();
		given(taskService.existsById(any())).willReturn(true);
		given(taskService.getTask(any())).willReturn(testTask);

		// When
		// Then
		assertThat(taskController.getTaskById(1L)).isEqualTo(ResponseEntity.ok(testTask));
	}

	@Test
	public void getTaskById_shouldReturn404ResponseWhenNoTaskFound() {
		// Given
		given(taskService.existsById(any())).willReturn(false);

		// When
		// Then
		assertThat(taskController.getTaskById(1L)).isEqualTo(ResponseEntity.notFound().build());
	}
}
