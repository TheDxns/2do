package io.github.thedxns.todo;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.thedxns.todo.logic.TaskService;
import io.github.thedxns.todo.model.Task;
import io.github.thedxns.todo.model.repositories.TaskRepository;

@SpringBootTest
public class TaskServiceTests {
    @Mock
	private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    public LocalDateTime getTestDate() {
        return LocalDateTime.parse("2019-05-08T11:33:08.863");
    }

    public void setUpTestTask() {
        Task task1 = new Task();
        task1.setTitle("A new test task");
        task1.setContent("<h1>This task was created for unit testing purpose.</h1>");
        task1.setCreatorId("user1234");
        task1.setPrioritized(true);
        task1.setDone(false);
        task1.setCreatedOn(getTestDate());
        doReturn(task1).when(taskRepository).getById((long) 1);
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
    @DisplayName("The context should not be null")
	public void contextLoads() throws Exception {
		Assertions.assertNotNull(taskService);
    }

    @Test
    @DisplayName("Successful creation of a task entity")
    public void createPostEntity() {
        setUpTestTask();
        Task newTask = new Task(taskRepository.getById((long) 1));
        Assertions.assertEquals("A new test task", newTask.getTitle());
        Assertions.assertEquals("<h1>This task was created for unit testing purpose.</h1>", newTask.getContent());
        Assertions.assertEquals("user1234", newTask.getCreatorId());
        Assertions.assertEquals(true, newTask.isPrioritized());
        Assertions.assertEquals(false, newTask.isDone());
        Assertions.assertEquals(getTestDate(), newTask.getCreatedOn());
    }

    @Test
    @DisplayName("After saving a task the sneak peak should not be null or too long")
    public void checkSneakPeakAfterSave() {
        setUpTestTask();
        TaskRepository mockTaskRepository = mock(TaskRepository.class);
        TaskService testTaskService = new TaskService(mockTaskRepository);
        Task newTask = new Task(taskRepository.getById((long) 1));
        newTask.setContent(generateRandomString(1000));
        testTaskService.saveTask(newTask);
    }

    @Test
    @DisplayName("After updating the task the sneak peak should not be null or too long")
    public void checkSneakPeakAfterUpdate() {
        setUpTestTask();
        TaskRepository mockTaskRepository = mock(TaskRepository.class);
        TaskService testTaskService = new TaskService(mockTaskRepository);
        Task newTask = new Task(taskRepository.getById((long) 1));
        newTask.setContent(generateRandomString(1000));
        testTaskService.updateTask((long)1, newTask);
    }
}
