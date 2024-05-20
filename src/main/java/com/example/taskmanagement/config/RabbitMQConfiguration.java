package com.example.taskmanagement.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${api.rabbitmq.queue}")
    private String queueName;

    @Value("${api.rabbitmq.exchange}")
    private String exchange;

    @Value("${api.rabbitmq.routingkey}")
    private String routingKey;

    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue taskUpdatesQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Binding taskUpdatesBinding(TopicExchange taskExchange, Queue taskUpdatesQueue) {
        return BindingBuilder.bind(taskUpdatesQueue).to(taskExchange).with(routingKey);
    }

}
