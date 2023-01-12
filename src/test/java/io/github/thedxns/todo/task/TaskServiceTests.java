package io.github.thedxns.todo.task;

import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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
        task1.setPriority(TaskPriority.MAJOR);
        task1.setStatus(TaskStatus.WAITING);
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
        final TaskDto newTask = TaskDto.from(taskRepository.getById((long) 1));
        Assertions.assertEquals("A new test task", newTask.getTitle());
        Assertions.assertEquals("<h1>This task was created for unit testing purpose.</h1>", newTask.getDescription());
        Assertions.assertEquals("user1234", newTask.getCreator().getKeycloakId());
        Assertions.assertEquals(TaskPriority.MAJOR, newTask.getPriority());
        Assertions.assertEquals(TaskStatus.WAITING, newTask.getStatus());
        Assertions.assertEquals(getTestDate(), newTask.getCreatedOn());
    }
}
