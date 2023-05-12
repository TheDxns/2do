package io.github.thedxns.todo.tasklist;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 200)
    @NotBlank(message = "The task title must not be empty")
    private String title;
    @Column(length = 200)
    @NotBlank
    private String ownerId;
    @ElementCollection(targetClass=Long.class)
    private List<Long> userIds;

    @PersistenceConstructor
    public TaskList() {
    }

    public TaskList(final TaskListDto dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.ownerId = dto.getOwner().getId();
        this.userIds = dto.getUserIds() != null ? new ArrayList<>(dto.getUserIds()) : null;
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

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}