package com.gila.challenge.notification.service;

import com.gila.challenge.notification.dto.MessageEvent;
import com.gila.challenge.notification.entity.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.stereotype.Service;

@Service
public class NotificationRabbitService {
  private final RabbitTemplate rabbitTemplate;

  public NotificationRabbitService(RabbitTemplate rabbitTemplate) {

    this.rabbitTemplate = rabbitTemplate;
  }

  public void notify(Message message, String routingKey, String exchange ) {

    rabbitTemplate.convertAndSend(exchange,routingKey, message);
  }

}
