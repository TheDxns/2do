package io.github.thedxns.todo.task;

import java.time.LocalDateTime;

public class TaskRequest {
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private Long creatorId;
    private Long taskListId;
    private LocalDateTime deadline;
    private Long responsibleId;

    public TaskRequest(String title, String description, TaskPriority priority, TaskStatus status, Long creatorId, Long taskListId, LocalDateTime deadline, Long responsibleId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creatorId = creatorId;
        this.taskListId = taskListId;
        this.deadline = deadline;
        this.responsibleId = responsibleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }
}
