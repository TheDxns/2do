package io.github.thedxns.todo.task;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class TaskControllerTests {

	private static TaskRequest testTaskRequest;

	@BeforeAll
	static void setUp() {
		testTaskRequest = new TaskRequest("Test", "Description", TaskPriority.MINOR, TaskStatus.WAITING,
				"123", 1L, LocalDateTime.now());
	}
    
	@Test
	@DisplayName("Delete task endpoint should return 404 Not Found response if there is no task of given ID")
	public void deletePostWhenNoPostFoundReturns404Response() {
		// Given
		final TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(false);
		final TaskController testPostController = new TaskController(mockTaskService);

		// When
		// Then
		Assertions.assertEquals(ResponseEntity.notFound().build(), testPostController.deleteTask((long) 1));
	}

	@Test
	@DisplayName("Update task endpoint should return 404 Not Found response if there is no task of given ID")
	public void updatePostWhenNoPostFoundReturns404Response() {
		// Given
		final TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(false);
		final TaskController testTaskController = new TaskController(mockTaskService);

		// When
		// Then
		Assertions.assertEquals(ResponseEntity.notFound().build(), testTaskController.updateTask((long) 1,
				any(TaskRequest.class)));
	}

	@Test
	@DisplayName("Delete task endpoint should return 500 Internal Error response if the task service could not delete the task")
	public void deletePostWhenPostCouldNotBeDeletedReturns500Response() {
		// Given
		final TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.deleteTask((long) 1)).thenReturn(false);
		final TaskController testTaskController = new TaskController(mockTaskService);

		// When
		// Then
		Assertions.assertEquals(ResponseEntity.internalServerError().build(), testTaskController.deleteTask((long) 1));
	}

	@Test
	@DisplayName("Update task endpoint should return 500 Internal Error response if the task service could not update the task")
	public void updatePostWhenPostCouldNotBeUpdatedReturns500Response() {
		// Given
		final TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.updateTask((long) 1, testTaskRequest)).thenReturn(false);
		final TaskController testPostController = new TaskController(mockTaskService);

		// When
		// Then
		Assertions.assertEquals(ResponseEntity.internalServerError().build(), testPostController.updateTask((long) 1,
				any(TaskRequest.class)));
	}

	@Test
	@DisplayName("Delete task endpoint should return 200 OK response if the task service deleted the task")
	public void deletePostWhenPostCouldBeDeletedReturns200Response() {
		// Given
		final TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.deleteTask((long) 1)).thenReturn(true);
		final TaskController testTaskController = new TaskController(mockTaskService);

		// When
		// Then
		Assertions.assertEquals(ResponseEntity.ok().build(), testTaskController.deleteTask((long) 1));
	}

	@Test
	@DisplayName("Update task endpoint should return 200 OK response if the task service updated the task")
	public void updatePostWhenPostCouldBeUpdatedReturns200Response() {
		// Given
		final TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.updateTask((long) 1, testTaskRequest)).thenReturn(true);
		final TaskController testTaskController = new TaskController(mockTaskService);

		// When
		// Then
		Assertions.assertEquals(ResponseEntity.internalServerError().build(), testTaskController.updateTask((long) 1,
				any(TaskRequest.class)));
	}
}
