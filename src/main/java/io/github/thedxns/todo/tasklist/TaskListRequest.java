package io.github.thedxns.todo.tasklist;

public class TaskListRequest {

    private String title;
    private long ownerId;

    public TaskListRequest(String title, long ownerId) {
        this.title = title;
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}