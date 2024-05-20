package com.example.taskmanagement.services.implementations;

import com.example.taskmanagement.dtos.responses.TaskResponse;
import com.example.taskmanagement.services.BroadcastService;
import com.example.taskmanagement.websocket.EventType;
import com.example.taskmanagement.websocket.WebSocketResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class BroadcastServiceImplementation implements BroadcastService {

    @Value("${api.rabbitmq.exchange}")
    private String exchange;

    @Value("${api.rabbitmq.routingkey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Async
    public void broadcastTask(TaskResponse task, EventType type) {
        try {

            WebSocketResponse response = WebSocketResponse.builder()
                    .timeStamp(Instant.now())
                    .event(type)
                    .data(task)
                    .build();
            String message = objectMapper.writeValueAsString(response);

            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (JsonProcessingException e) {
            log.info("Error converting to Json");
        }
    }

}
