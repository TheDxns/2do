package io.github.thedxns.todo.tasklist;

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

    @ElementCollection(targetClass=String.class)
    private List<String> users;

    public void updateFrom(final TaskList source) {
        this.title = source.title;
        this.users = source.users;
    }
    public TaskList() {
        
    }

    public TaskList(Long id, String title, List<String> users) {
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