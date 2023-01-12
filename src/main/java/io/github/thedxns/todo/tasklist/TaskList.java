package io.github.thedxns.todo.tasklist;

import io.github.thedxns.todo.user.KeycloakId;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    @ElementCollection(targetClass=String.class)
    private List<String> users;

    @PersistenceConstructor
    public TaskList() {
    }

    public TaskList(final TaskListDto dto) {
        this.title = dto.getTitle();
        this.users = dto.getUsers().stream().map(KeycloakId::getId).collect(Collectors.toList());
    }
    public void updateFromDto(final TaskListDto dto) {
        this.title = dto.getTitle();
        this.users = dto.getUsers().stream().map(Object::toString).collect(Collectors.toList());
    }

    public TaskList(final String title, final List<String> users) {
        this.title = title;
        this.users = users;
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

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}