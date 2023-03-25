package io.github.thedxns.todo.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/notify")
    public void sendNotification(NotificationRequest payload) {
        messagingTemplate.convertAndSend("/topic/notifications", payload);
    }

}