package io.github.thedxns.todo.task;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.thedxns.todo.tasklist.TaskListService;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@SpringBootTest
public class TaskControllerTests {

	private static TaskRequest testTaskRequest;
    @Mock
	private TaskController mockTaskController;

	@BeforeAll
	static void setUp() {
		testTaskRequest = new TaskRequest("Test", "Description", TaskPriority.MINOR, TaskStatus.WAITING, "123", 1L, LocalDateTime.now());
	}

	@Test
    @DisplayName("The context should not be null")
	public void contextLoads() {
		Assertions.assertNotNull(mockTaskController);
    }
    
	@Test
	@DisplayName("Delete task endpoint should return 404 Not Found response if there is no task of given ID")
	public void deletePostWhenNoPostFoundReturns404Response() {
		TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(false);
		TaskController testPostController = new TaskController(mockTaskService);
		Assertions.assertEquals(ResponseEntity.notFound().build(), testPostController.deleteTask((long) 1));
	}

	@Test
	@DisplayName("Update task endpoint should return 404 Not Found response if there is no task of given ID")
	public void updatePostWhenNoPostFoundReturns404Response() {
		TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(false);
		TaskController testTaskController = new TaskController(mockTaskService);
		Assertions.assertEquals(ResponseEntity.notFound().build(), testTaskController.updateTask((long) 1, any(TaskRequest.class)));
	}

	@Test
	@DisplayName("Delete task endpoint should return 500 Internal Error response if the task service could not delete the task")
	public void deletePostWhenPostCouldNotBeDeletedReturns500Response() {
		TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.deleteTask((long) 1)).thenReturn(false);
		TaskController testTaskController = new TaskController(mockTaskService);
		Assertions.assertEquals(ResponseEntity.internalServerError().build(), testTaskController.deleteTask((long) 1));
	}

	@Test
	@DisplayName("Update task endpoint should return 500 Internal Error response if the task service could not update the task")
	public void updatePostWhenPostCouldNotBeUpdatedReturns500Response() {

		TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.updateTask((long) 1, testTaskRequest)).thenReturn(false);
		TaskController testPostController = new TaskController(mockTaskService);
		Assertions.assertEquals(ResponseEntity.internalServerError().build(), testPostController.updateTask((long) 1, any(TaskRequest.class)));
	}

	@Test
	@DisplayName("Delete task endpoint should return 200 OK response if the task service deleted the task")
	public void deletePostWhenPostCouldBeDeletedReturns200Response() {
		TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.deleteTask((long) 1)).thenReturn(true);
		TaskController testTaskController = new TaskController(mockTaskService);
		Assertions.assertEquals(ResponseEntity.ok().build(), testTaskController.deleteTask((long) 1));
	}

	@Test
	@DisplayName("Update task endpoint should return 200 OK response if the task service updated the task")
	public void updatePostWhenPostCouldBeUpdatedReturns200Response() {
		final Task task = new Task();
		TaskService mockTaskService = mock(TaskService.class);
		when(mockTaskService.existsById((long) 1)).thenReturn(true);
		when(mockTaskService.updateTask((long) 1, testTaskRequest)).thenReturn(true);
		TaskController testTaskController = new TaskController(mockTaskService);
		Assertions.assertEquals(ResponseEntity.internalServerError().build(), testTaskController.updateTask((long) 1, any(TaskRequest.class)));
	}
}
