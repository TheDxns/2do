package io.github.thedxns.todo.notification;

import io.github.thedxns.todo.task.TaskDto;
import io.github.thedxns.todo.task.TaskService;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class NotificationScheduler {

    private final TaskService taskService;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationScheduler(TaskService taskService, SimpMessagingTemplate simpMessagingTemplate) {
        this.taskService = taskService;
        this.messagingTemplate = simpMessagingTemplate;
    }

    //TODO: Move the delay value to properties
    @Scheduled(fixedDelay = 60000) // It will run every minute
    @Transactional
    public void sendNotifications() {
        final List<TaskDto> approachingTasks = taskService.findApproachingTasks();

        for (TaskDto task : approachingTasks) {
            sendNotification(task);
        }
    }

    private void sendNotification(TaskDto task) {
        final String message = "Task '" + task.getTitle() + "' deadline is " + task.getDeadline();
        final NotificationRequest notification = new NotificationRequest(message, "red");
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}
