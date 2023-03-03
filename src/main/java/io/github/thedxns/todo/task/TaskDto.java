package io.github.thedxns.todo.task;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.user.UserDto;

import java.time.LocalDateTime;

public class TaskDto {
    private final String title;
    private final String description;
    private final TaskPriority priority;
    private final TaskStatus status;
    private final UserDto creator;
    private final TaskListDto taskList;
    private final LocalDateTime deadline;
    private final LocalDateTime createdOn;
    private final LocalDateTime updatedOn;

    public TaskDto(String title, String description, TaskPriority priority, TaskStatus status, UserDto creator,
                   TaskListDto taskList, LocalDateTime deadline, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creator = creator;
        this.taskList = taskList;
        this.deadline = deadline;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public TaskDto(String title, String description, TaskPriority priority, TaskStatus status, UserDto creator,
                   TaskListDto taskList, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creator = creator;
        this.taskList = taskList;
        this.deadline = deadline;
        this.createdOn = null;
        this.updatedOn = null;
    }

    public static TaskDto from(Task task) {
        return new TaskDto(task.getTitle(), task.getContent(), task.getPriority(), task.getStatus(),
                new UserDto(task.getCreatorId(), task.getResponsible()), TaskListDto.from(task.getTaskList()),
                task.getDeadline(), task.getCreatedOn(), task.getUpdatedOn());
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
}
