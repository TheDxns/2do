package io.github.thedxns.todo.task;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.tasklist.TaskListService;
import io.github.thedxns.todo.user.UserDto;
import io.github.thedxns.todo.user.UserService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskListService taskListService;
    private final UserService userService;
    private final TaskRepository taskRepository;

    public TaskService(TaskListService taskListService, UserService userService, TaskRepository taskRepository) {
        this.taskListService = taskListService;
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream().map(task -> TaskDto.from(task, userService.getUserById(
                task.getCreatorId()))).collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByKeyword(final String keyword) {
        final List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(keyword);
        tasks.addAll(taskRepository.findByContentContainingIgnoreCase(keyword));
        final Set<Task> set = new LinkedHashSet<>(tasks);
        tasks.clear();
        tasks.addAll(set);
        return tasks.stream().map(task -> TaskDto.from(task, userService.getUserById(task.getCreatorId())))
                .collect(Collectors.toList());
    }
    
    public TaskDto getTask(final Long id) {
        final Optional<Task> task = taskRepository.findById(id);
        return task.map(v -> TaskDto.from(v, userService.getUserById(v.getCreatorId()))).orElse(null);
    }

    public List<TaskDto> getTasksByList(final Long id) {
        return taskRepository.findByTaskListId(id).stream().map(task -> TaskDto.from(task, userService.getUserById(
                task.getCreatorId()))).collect(Collectors.toList());
    } 

    public List<TaskDto> getAllByCreator(final long creatorId) {
        return taskRepository.findByCreatorId(creatorId).stream().map(task -> TaskDto.from(task, userService.getUserById(
                task.getCreatorId()))).collect(Collectors.toList());
    }

    public List<TaskDto> getUnfinishedByCreator(final long creatorId) {
        final List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> !t.getStatus().equals(TaskStatus.DONE) && !t.getStatus().equals(TaskStatus.DELETED) &&
                    t.getTaskList() == null).map(task -> TaskDto.from(task, userService.getUserById(
                        task.getCreatorId()))).collect(Collectors.toList());
    }

    public List<TaskDto> getDoneByCreator(final long creatorId) {
        final List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> t.getStatus().equals(TaskStatus.DONE) && t.getTaskList() == null).map(
                    task -> TaskDto.from(task, userService.getUserById(task.getCreatorId()
                    ))).collect(Collectors.toList());
    }

    public List<TaskDto> getImportantByCreator(final long creatorId) {
        final List<Task> allTasks = taskRepository.findByCreatorId(creatorId);
        return allTasks.stream()
            .filter(t -> !t.getStatus().equals(TaskStatus.DONE) && !t.getStatus().equals(TaskStatus.DELETED)
                    && t.getPriority().equals(TaskPriority.MAJOR)).map(task -> TaskDto.from(task, userService.getUserById(
                        task.getCreatorId()))).collect(Collectors.toList());
    }

    public List<TaskDto> getCustom(final Long listId) {
        final List<Task> allTasks = taskRepository.findByTaskListId(listId);
        return allTasks.stream()
            .filter(t -> !t.getStatus().equals(TaskStatus.DONE) && !t.getStatus().equals(TaskStatus.DELETED)
                    && t.getTaskList() != null).map(task -> TaskDto.from(task, userService.getUserById(
                        task.getCreatorId()))).collect(Collectors.toList());
    }

    public boolean saveTask(final TaskRequest taskData) {
        final TaskListDto taskList;
        if (taskData.getTaskListId() != null) {
            taskList = taskListService.getTaskList(taskData.getTaskListId());
        } else {
            taskList = null;
        }

        final UserDto creator = userService.getUserById(taskData.getCreatorId());
        final TaskDto task = new TaskDto(taskData.getTitle(), taskData.getDescription(), taskData.getPriority(),
                TaskStatus.WAITING, creator, taskList, taskData.getDeadline(), taskData.getResponsibleId());
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

    public boolean updateTask(final Long id, final TaskRequest taskData) {
        final TaskListDto taskList = taskListService.getTaskList(taskData.getTaskListId());
        final UserDto creator = userService.getUserById(taskData.getCreatorId());
        final TaskDto task = new TaskDto(id, taskData.getTitle(), taskData.getDescription(), taskData.getPriority(),
                taskData.getStatus(), creator, taskList, taskData.getDeadline(), taskData.getResponsibleId());

        taskRepository.save(new Task(task));
        return true;
    }

    public boolean saveCustomListTask(final Long id, final TaskRequest taskData) {
        final TaskListDto taskList = taskListService.getTaskList(id);
        final UserDto creator = userService.getUserById(taskData.getCreatorId());
        final TaskDto task = new TaskDto(id, taskData.getTitle(), taskData.getDescription(), taskData.getPriority(),
                TaskStatus.WAITING, creator, taskList, taskData.getDeadline(), taskData.getResponsibleId());
        taskRepository.save(new Task(task));
        return true;
    }

    public boolean switchPriority(final Long id) {
        final Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            if (task.get().getPriority().equals(TaskPriority.MINOR)) {
                task.get().setPriority(TaskPriority.MAJOR);
            } else {
                task.get().setPriority(TaskPriority.MINOR);
            }
            taskRepository.save(task.get());
            return true;
        } else {
            throw new RuntimeException("Task with given ID does not exist.");
        }
    }

    public boolean finishTask(final Long id) {
        final Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            task.get().setTaskList(null);
            task.get().setStatus(TaskStatus.DONE);
            taskRepository.save(task.get());
            return true;
        } else {
            throw new RuntimeException("Task with given ID does not exist.");
        }
    }

    public List<TaskDto> findApproachingTasks() {
        final LocalDateTime oneHourFromNow = LocalDateTime.now().plusHours(1);
        final List<Task> tasks = taskRepository.findByDeadline(oneHourFromNow);
        return tasks.stream().map(task -> TaskDto.from(task, userService.getUserById(
                task.getCreatorId()))).collect(Collectors.toList());
    }
}