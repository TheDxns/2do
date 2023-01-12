package io.github.thedxns.todo.tasklist;

import io.github.thedxns.todo.user.KeycloakId;

import java.util.List;
import java.util.stream.Collectors;

public class TaskListDto {

    private final TaskListId taskListId;
    private final String title;
    private final List<KeycloakId> users;

    public TaskListDto(TaskListId taskListId, String title, List<KeycloakId> users) {
        this.taskListId = taskListId;
        this.title = title;
        this.users = users;
    }

    public static TaskListDto from(TaskList taskList) {
        return new TaskListDto(new TaskListId(taskList.getId()), taskList.getTitle(), taskList.getUsers().stream().map(KeycloakId::new).collect(Collectors.toList()));
    }

    public TaskListId getTaskListId() {
        return taskListId;
    }

    public String getTitle() {
        return title;
    }

    public List<KeycloakId> getUsers() {
        return users;
    }

}
