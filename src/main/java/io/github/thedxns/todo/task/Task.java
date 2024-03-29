package io.github.thedxns.todo.task;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import io.github.thedxns.todo.tasklist.TaskList;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.PersistenceConstructor;

@Entity
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 200)
    @NotBlank(message = "The task title must not be empty")
    private String title;
    @Column(length = 100000)
    private String content;
    private TaskPriority priority;
    private TaskStatus status;
    @NotBlank(message = "The ID of the creator of the post must be set")
    private Long creatorId;
    private Long responsibleId;
    @ManyToOne
    @JoinColumn(name = "task_list_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TaskList taskList;
    private LocalDateTime deadline;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PersistenceConstructor
    public Task() {
    }

    public Task(TaskDto source) {
        id = source.getId();
        title = source.getTitle();
        content = source.getDescription();
        priority = source.getPriority();
        creatorId = source.getCreator().getId();
        status = source.getStatus();
        deadline = source.getDeadline();
        responsibleId = source.getResponsibleUserId();
        taskList = source.getTaskList() != null ? new TaskList(source.getTaskList()) : null;
    }

    @PrePersist
    void prePersist() {
        this.setCreatedOn(LocalDateTime.now());
    }

    @PreUpdate
    void preUpdate() {
        this.setUpdatedOn(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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