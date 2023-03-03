package io.github.thedxns.todo.task;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.tasklist.TaskListService;
import io.github.thedxns.todo.user.KeycloakId;
import io.github.thedxns.todo.user.UserDto;
import io.github.thedxns.todo.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class TaskService {

    private final TaskListService taskListService;
    private final UserService userService;
    private final TaskRepository taskRepository;

    public TaskService(TaskListService taskListService, UserService userService, TaskRepository taskRepository) {
        this.taskListService = taskListService;
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasks(final Pageable page) {
        return taskRepository.findAll(page).getContent();
    }

    public List<Task> getTasksByKeyword(final String keyword) {
        final List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(keyword);
        tasks.addAll(taskRepository.findByContentContainingIgnoreCase(keyword));
        final Set<Task> set = new LinkedHashSet<>(tasks);
        tasks.clear();
        tasks.addAll(set);
        return tasks;
    }
    
    public Task getTask(final Long id) {
        return taskRepository.findById(id).get();
    }

    public List<Task> getTasksByList(final Long id) {
        return taskRepository.findByTaskListId(id);
    } 

    public List<Task> getAllByCreator(final String creatorId) {
        return taskRepository.findByCreatorId(creatorId);
    }

    public List<Task> getUnfinishedByCreator(final String creatorId) {
        List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> !t.getStatus().equals(TaskStatus.DONE) && t.getTaskList() == null).collect(Collectors.toList());
    }

    public List<Task> getDoneByCreator(final String creatorId) {
        List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> t.getStatus().equals(TaskStatus.DONE) && t.getTaskList() == null).collect(Collectors.toList());
    }

    public List<Task> getImportantByCreator(final String creatorId) {
        List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> !t.getStatus().equals(TaskStatus.DONE) && t.getPriority().equals(TaskPriority.MAJOR)).collect(Collectors.toList());
    }

    public List<Task> getCustom(final Long listId) {
        List<Task> allTasks = taskRepository.findByTaskListId(listId);
        return allTasks.stream()
            .filter(t -> !t.getStatus().equals(TaskStatus.DONE) && t.getTaskList() != null).collect(Collectors.toList());
    }

    public boolean saveTask(final TaskRequest taskData) {
        final TaskListDto taskList = taskListService.getTaskList(taskData.getTaskListId());

        final UserDto creator = userService.getUserById(new KeycloakId(taskData.getCreatorId()));
        final TaskDto task = new TaskDto(taskData.getTitle(), taskData.getDescription(), taskData.getPriority(),
                taskData.getStatus(), creator, taskList, taskData.getDeadline());
        taskRepository.save(new Task(task));
        return true;
    }

    public boolean deleteTask(final Long id) {
        taskRepository.deleteById(id);
        return true;
    }

    public boolean existsById(final Long id) {
        return taskRepository.existsById(id);
    }

    public boolean updateTask(final Long id, final Task task) {
        task.setId(id);
        task.updateFrom(task);
        taskRepository.save(task);
        return true;
    }

    public boolean saveCustomListTask(Long id, TaskDto task) {
        //TODO: Finish this
        return true;
    }
}