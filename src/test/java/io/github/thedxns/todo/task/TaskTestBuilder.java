package io.github.thedxns.todo.task;

import io.github.thedxns.todo.tasklist.TaskListDto;
import io.github.thedxns.todo.user.UserDto;

import java.time.LocalDateTime;

public class TaskTestBuilder {
    private Long id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private UserDto creator;
    private TaskListDto taskList;
    private LocalDateTime deadline;
    private long responsibleId;

    public TaskTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TaskTestBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TaskTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TaskTestBuilder priority(TaskPriority priority) {
        this.priority = priority;
        return this;
    }

    public TaskTestBuilder status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public TaskTestBuilder creator(UserDto creator) {
        this.creator = creator;
        return this;
    }

    public TaskTestBuilder taskList(TaskListDto taskList) {
        this.taskList = taskList;
        return this;
    }

    public TaskTestBuilder deadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public TaskTestBuilder responsibleId(long responsibleId) {
        this.responsibleId = responsibleId;
        return this;
    }

    public TaskDto build() {
        if (id != null) {
            return new TaskDto(id, title, description, priority, status, creator, taskList, deadline, responsibleId);
        } else {
            return new TaskDto(title, description, priority, status, creator, taskList, deadline, responsibleId);
        }
    }
}