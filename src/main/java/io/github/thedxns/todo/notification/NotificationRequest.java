package io.github.thedxns.todo.notification;

public class NotificationRequest {

    private String message;
    private String color;

    public NotificationRequest(String message, String color) {
        this.message = message;
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
