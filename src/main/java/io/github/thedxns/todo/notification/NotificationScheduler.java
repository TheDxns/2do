package io.github.thedxns.todo.notification;

import io.github.thedxns.todo.task.TaskDto;
import io.github.thedxns.todo.task.TaskService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class NotificationScheduler {

    private final TaskService taskService;
    private final RestTemplate restTemplate;

    public NotificationScheduler(TaskService taskService) {
        this.taskService = taskService;
        this.restTemplate = new RestTemplate();
    }

    //TODO: Move the delay value to properties
    @Scheduled(fixedDelay = 60000) // Means it will run every minute
    public void sendNotifications() {
        List<TaskDto> approachingTasks = taskService.findApproachingTasks();

        for (TaskDto task : approachingTasks) {
            sendNotification(task);
        }
    }

    private void sendNotification(TaskDto task) {
        final String message = "Task '" + task.getTitle() + "' deadline is " + task.getDeadline();
        final NotificationRequest notification = new NotificationRequest(message, "warning");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<NotificationRequest> entity = new HttpEntity<>(notification, headers);

        restTemplate.postForObject("http://localhost:80/notifications", entity, Void.class);
    }
}
