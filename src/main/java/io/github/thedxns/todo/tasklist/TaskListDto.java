package io.github.thedxns.todo.tasklist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskListDto {

    private final Long id;
    private final String title;
    private final Long ownerId;
    private final List<Long> userIds;

    public TaskListDto(Long id, String title, Long ownerId, List<Long> userIds) {
        this.id = id;
        this.title = title;
        this.ownerId = ownerId;
        this.userIds = userIds;
    }

    public static TaskListDto from(TaskList taskList) {
        if (taskList != null) {
            return new TaskListDto(taskList.getId(), taskList.getTitle(), taskList.getOwnerId(),
                    new ArrayList<>(taskList.getUserIds()));
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }
}
