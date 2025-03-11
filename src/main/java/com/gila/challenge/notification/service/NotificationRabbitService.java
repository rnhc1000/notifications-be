package com.gila.challenge.notification.service;

import com.gila.challenge.notification.entity.Message;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class NotificationRabbitService {
    private final RabbitTemplate rabbitTemplate;

    public NotificationRabbitService(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    public void notify(Message message, String routingKey, String exchange) {

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (RuntimeException ex) {

            throw new AmqpException(ex.getMessage());
        }
    }

}
