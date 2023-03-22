package io.github.thedxns.todo.task;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.user.KeycloakId;
import io.github.thedxns.todo.user.UserDto;

import java.time.LocalDateTime;

public class TaskDto {
    private final Long id;
    private final String title;
    private final String description;
    private final TaskPriority priority;
    private final TaskStatus status;
    private final UserDto creator;
    private final TaskListDto taskList;
    private final LocalDateTime deadline;
    private final KeycloakId responsible;

    public TaskDto(Long id, String title, String description, TaskPriority priority, TaskStatus status, UserDto creator,
                   TaskListDto taskList, LocalDateTime deadline, KeycloakId responsible) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creator = creator;
        this.taskList = taskList;
        this.deadline = deadline;
        this.responsible = responsible;
    }

    public TaskDto(String title, String description, TaskPriority priority, TaskStatus status, UserDto creator,
                   TaskListDto taskList, LocalDateTime deadline, KeycloakId responsible) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creator = creator;
        this.taskList = taskList;
        this.deadline = deadline;
        this.responsible = responsible;
    }

    public static TaskDto from(Task task, UserDto user) {
        return new TaskDto(task.getId(), task.getTitle(), task.getContent(), task.getPriority(), task.getStatus(),
                user, TaskListDto.from(task.getTaskList()), task.getDeadline(), new KeycloakId(task.getResponsible()));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public UserDto getCreator() {
        return creator;
    }

    public TaskListDto getTaskList() {
        return taskList;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public KeycloakId getResponsible() {
        return responsible;
    }
}
