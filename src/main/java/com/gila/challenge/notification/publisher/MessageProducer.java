package com.gila.challenge.notification.publisher;

import com.gila.challenge.notification.dto.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//@Service
public class MessageProducer {
  private final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

  @Value ("${rabbitmq.exchange.message.name}")
  private String exchange;
  @Value ("${rabbitmq.binding.message.routing.key}")
  private String messageRoutingKey;
  @Value ("${rabbitmq.binding.email.routing.key}")
  private String emailRoutingKey;

  @Value ("${rabbitmq.binding.sns.routing.key}")
  private String snsRoutingKey;
  @Autowired
  public RabbitTemplate rabbitTemplate;

  public void forwardMessage(MessageEvent messageEvent) {
    LOGGER.info(String.format("Message event sent to RabbitMQ => %s", messageEvent.toString()));
    rabbitTemplate.convertAndSend(exchange, emailRoutingKey, messageEvent);
    rabbitTemplate.convertAndSend(exchange, messageRoutingKey, messageEvent);
    rabbitTemplate.convertAndSend(exchange, snsRoutingKey, messageEvent);


  }


}
