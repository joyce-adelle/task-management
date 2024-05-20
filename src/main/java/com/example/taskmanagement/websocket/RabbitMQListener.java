package com.example.taskmanagement.websocket;

import com.example.taskmanagement.entities.Task;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Async
    @RabbitListener(queues = "task.updates")
    public void receiveTaskUpdate(String task) {
        try {
            messagingTemplate.convertAndSend("/topic/task.updates", task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
