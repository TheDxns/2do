package io.github.thedxns.todo.task;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import io.github.thedxns.todo.tasklist.TaskList;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 200)
    @NotBlank(message = "The task title must not be empty")
    private String title;

    @Column(length = 100000)
    private String content;

    private boolean prioritized;

    private boolean isDone;

    @NotBlank(message = "The ID of the creator of the post must be set")
    private String creatorId;

    private String responsible;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TaskList taskList;

    private LocalDateTime deadline;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    public Task() {
    }

    public Task(Task source) {
        title = source.title;
        content = source.content;
        createdOn = source.createdOn;
        updatedOn = source.updatedOn;
        prioritized = source.prioritized;
        creatorId = source.creatorId;
        isDone = source.isDone;
        deadline = source.deadline;
        responsible = source.responsible;
    }
    
    public void updateFrom(final Task source) {
        title = source.title;
        content = source.content;
        createdOn = source.createdOn;
        updatedOn = source.updatedOn;
        prioritized = source.prioritized;
        creatorId = source.creatorId;
        isDone = source.isDone;
        deadline = source.deadline;
        responsible = source.responsible;
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

    public boolean isPrioritized() {
        return prioritized;
    }

    public void setPrioritized(boolean prioritized) {
        this.prioritized = prioritized;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}