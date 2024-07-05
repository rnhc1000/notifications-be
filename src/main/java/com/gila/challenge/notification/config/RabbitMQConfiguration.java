package com.gila.challenge.notification.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
  @Value ("${rabbitmq.queue.message.name}")
  private String queueMessage;

  @Value ("${rabbitmq.binding.message.routing.key}")
  private String messageRoutingKey;

  @Value ("${rabbitmq.exchange.message.name}")
  private String exchangeMessage;

  @Value ("${rabbitmq.queue.email.name}")
  private String queueEmail;

  @Value ("${rabbitmq.binding.email.routing.key}")
  private String emailRoutingKey;

  @Value ("${rabbitmq.queue.sns.name}")
  private String queueSns;

  @Value ("${rabbitmq.binding.sns.routing.key}")
  private String snsRoutingKey;

  @Bean
  public Queue queueMessage() {
    return new Queue(queueMessage);
  }

  @Bean
  public Queue queueEmail() {
    return new Queue(queueEmail);
  }

  @Bean
  public Queue queueSns() {
    return new Queue(queueSns);
  }

  @Bean
  public TopicExchange exchangeMessage() {
    return ExchangeBuilder.topicExchange(exchangeMessage).build();
  }

  @Bean
  public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
    return event -> rabbitAdmin.initialize();
  }

  @Bean
  public Binding messageBinding() {
    return BindingBuilder
            .bind(queueMessage())
            .to(exchangeMessage())
            .with(messageRoutingKey);
  }

  @Bean
  public Binding emailBinding() {
    return BindingBuilder
            .bind(queueEmail())
            .to(exchangeMessage())
            .with(emailRoutingKey);
  }

  @Bean
  public Binding snsBinding() {
    return BindingBuilder
            .bind(queueSns())
            .to(exchangeMessage())
            .with(snsRoutingKey);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }
}
