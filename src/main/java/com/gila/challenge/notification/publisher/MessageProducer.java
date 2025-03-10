package com.gila.challenge.notification.publisher;

import com.gila.challenge.notification.dto.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
  private final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

  @Value("${rabbitmq.exchange.messages.name}")
  private String exchange;

  @Value("${rabbitmq.binding.messages.routing.key}")
  private String messageRoutingKey;

  @Value("${rabbitmq.binding.email.routing.key}")
  private String emailRoutingKey;

  @Value("${rabbitmq.binding.sns.routing.key}")
  private String snsRoutingKey;

  public final RabbitTemplate rabbitTemplate;

  public MessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void forwardMessage(MessageEvent messageEvent) {
    logger.info("::: Message event sent to RabbitMQ => {}", messageEvent);

    rabbitTemplate.convertAndSend(exchange, emailRoutingKey, messageEvent);
    rabbitTemplate.convertAndSend(exchange, messageRoutingKey, messageEvent);
    rabbitTemplate.convertAndSend(exchange, snsRoutingKey, messageEvent);
  }

}
