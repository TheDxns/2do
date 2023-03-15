package io.github.thedxns.todo.tasklist;

import io.github.thedxns.todo.user.KeycloakId;

import java.util.List;
import java.util.stream.Collectors;

public class TaskListDto {

    private final Long id;
    private final String title;
    private final KeycloakId owner;
    private final List<KeycloakId> users;

    public TaskListDto(Long id, String title, KeycloakId owner, List<KeycloakId> users) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.users = users;
    }

    public static TaskListDto from(TaskList taskList) {
        if (taskList != null) {
            return new TaskListDto(taskList.getId(), taskList.getTitle(), new KeycloakId(taskList.getOwnerId()),
                    taskList.getUsers().stream().map(KeycloakId::new).collect(Collectors.toList()));
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public KeycloakId getOwner() {
        return owner;
    }

    public List<KeycloakId> getUsers() {
        return users;
    }
}
