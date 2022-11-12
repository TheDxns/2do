package io.github.thedxns.todo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import io.github.thedxns.todo.model.Task;
import io.github.thedxns.todo.controller.TaskController;
import io.github.thedxns.todo.logic.TaskService;

@SpringBootTest
public class TaskControllerTests {
    @Mock
	private TaskController taskController;

	@Test
    @DisplayName("The context should not be null")
	public void contextLoads() throws Exception {
		Assertions.assertNotNull(taskController);
    }
    
    /*@Test
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
        when(mockTaskService.existsById((long)1)).thenReturn(false);
        TaskController testTaskController = new TaskController(mockTaskService);
        Assertions.assertEquals(ResponseEntity.notFound().build(), testTaskController.updateTask((long) 1, any(Task.class)));
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
        when(mockTaskService.updateTask((long) 1, new Task())).thenReturn(false);
        TaskController testPostController = new TaskController(mockTaskService);
        Assertions.assertEquals(ResponseEntity.internalServerError().build(), testPostController.updateTask((long) 1, any(Task.class)));
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
        TaskService mockTaskService = mock(TaskService.class);
        when(mockTaskService.existsById((long) 1)).thenReturn(true);
        when(mockTaskService.updateTask((long) 1, new Task())).thenReturn(true);
        TaskController testTaskController = new TaskController(mockTaskService);
        Assertions.assertEquals(ResponseEntity.internalServerError().build(), testTaskController.updateTask((long) 1, any(Task.class)));
    }*/
}
