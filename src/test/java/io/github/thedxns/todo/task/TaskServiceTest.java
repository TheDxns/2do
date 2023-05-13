package io.github.thedxns.todo.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.tasklist.TaskListService;
import io.github.thedxns.todo.user.UserDto;
import io.github.thedxns.todo.user.UserService;
import io.github.thedxns.todo.user.UserTestBuilder;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TaskServiceTest {

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
        final TaskListDto taskList = new TaskListDto(1L, "Test", 12L, Collections.emptyList());
        return new TaskTestBuilder().id(1L).taskList(taskList).title("Test").description("Test task")
                .priority(TaskPriority.MINOR).status(TaskStatus.WAITING).creator(prepareTestUser()).deadline(LocalDateTime.now())
                .responsibleId(5L).build();
    }

    private List<Task> prepareRandomTestTasks() {
        final TaskListDto taskList = new TaskListDto(1L, "Test", 12L, Collections.emptyList());
        final List<Task> tasks = new ArrayList<>();

        tasks.add(new Task(new TaskTestBuilder().id(1L).taskList(taskList).title("Test").description("Test task")
                .priority(TaskPriority.MINOR).status(TaskStatus.WAITING).creator(prepareTestUser()).deadline(LocalDateTime.now())
                .responsibleId(12L).build()));

        tasks.add(new Task(new TaskTestBuilder().id(2L).taskList(null).title("Test").description("Test task")
                .priority(TaskPriority.MINOR).status(TaskStatus.IN_PROGRESS).creator(prepareTestUser()).deadline(LocalDateTime.now())
                .responsibleId(12L).build()));

        tasks.add(new Task(new TaskTestBuilder().id(3L).taskList(null).title("Test").description("Test task")
                .priority(TaskPriority.MAJOR).status(TaskStatus.DONE).creator(prepareTestUser()).deadline(LocalDateTime.now())
                .responsibleId(12L).build()));

        tasks.add(new Task(new TaskTestBuilder().id(4L).taskList(null).title("Test").description("Test task")
                .priority(TaskPriority.MAJOR).status(TaskStatus.IN_PROGRESS).creator(prepareTestUser()).deadline(LocalDateTime.now())
                .responsibleId(12L).build()));

        tasks.add(new Task(new TaskTestBuilder().id(5L).taskList(null).title("Test").description("Test task")
                .priority(TaskPriority.MAJOR).status(TaskStatus.DELETED).creator(prepareTestUser()).deadline(LocalDateTime.now())
                .responsibleId(12L).build()));

        return tasks;
    }

    private UserDto prepareTestUser() {
        final List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UserTestBuilder().id(1L).username("jdoe").firstName("John").surname("Doe").email("jdoe@mail.com").roles(roles).build();
    }

    @Test
    public void getAllTasks_shouldReturnAllTasksFromRepository() {
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
    public void switchTaskPriority_shouldSwitchTaskPriorityIfTaskExists() {
        // Given
        final Task task = new Task(prepareTestTask());
        given(taskRepositoryMock.findById(any())).willReturn(Optional.of(task));

        // When
        taskService.switchPriority(1L);

        // Then
        verify(taskRepositoryMock, times(1)).save(any());
        assertDoesNotThrow(() -> taskService.switchPriority(1L));
        assertThat(taskService.switchPriority(1L)).isTrue();
    }

    @Test
    public void switchTaskPriority_shouldThrowExceptionIfTaskNotExists() {
        // Given
        given(taskRepositoryMock.findById(any())).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> taskService.switchPriority(1L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void finishTask_shouldShouldChangeTaskStatusWhenTaskExists() {
        // Given
        final Task task = new Task(prepareTestTask());
        given(taskRepositoryMock.findById(any())).willReturn(Optional.of(task));

        // When
        taskService.finishTask(1L);

        // Then
        verify(taskRepositoryMock, times(1)).save(any());
        assertDoesNotThrow(() -> taskService.finishTask(1L));
        assertThat(taskService.finishTask(1L)).isTrue();
    }

    @Test
    public void finishTask_shouldThrowExceptionIfTaskNotExists() {
        // Given
        given(taskRepositoryMock.findById(any())).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> taskService.finishTask(1L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getUnfinishedByCreator_shouldReturnNonDoneAndNonDeletedUnfinishedTasksThatAreNotOnCustomList() {
        // Given
        given(taskRepositoryMock.findByCreatorId(anyLong())).willReturn(prepareRandomTestTasks());

        // When
        // Then
        assertThat(taskService.getUnfinishedByCreator(15L)).hasSize(2);
    }

    @Test
    public void getImportantByCreator_shouldReturnNonDoneAndNonDeletedTasksWithMajorPriorityThatAreNotOnCustomList() {
        // Given
        given(taskRepositoryMock.findByCreatorId(anyLong())).willReturn(prepareRandomTestTasks());

        // When
        // Then
        assertThat(taskService.getImportantByCreator(15L)).hasSize(1);
    }
    @Test
    public void getDoneByCreator_shouldReturnDoneAndNonDeletedTasksThatAreNotOnCustomList() {
        // Given
        given(taskRepositoryMock.findByCreatorId(anyLong())).willReturn(prepareRandomTestTasks());

        // When
        // Then
        assertThat(taskService.getDoneByCreator(15L)).hasSize(1);
    }

    @Test
    public void getTasksByKeyword_shouldReturnTasksThatContainGivenKeyAndThatAreNotOnCustomList() {
        // Given
        given(taskRepositoryMock.findByTitleContainingIgnoreCase(any())).willReturn(prepareRandomTestTasks());

        // When
        // Then
        assertThat(taskService.getTasksByKeyword("Test")).hasSize(5);
    }

    @Test
    public void getCustom_shouldReturnNonDoneAndNonDeletedTasksThatAreOnCustomList() {
        // Given
        given(taskRepositoryMock.findByTaskListId(anyLong())).willReturn(prepareRandomTestTasks());

        // When
        // Then
        assertThat(taskService.getCustom(1L)).hasSize(1);
    }
}
