**## Take-home Microservices Challenge
This is how we faced the challenge of creating some microservices
to consume notifications services.**
## _Table of contents_
- [Challenge code - @Ricardo Ferreira])
- [_Table of contents_](#table-of-contents)
- [_Overview_](#overview)
- [_Screenshot_](#screenshot)
- [_Links_](#links)
- [_Built with_](#built-with)
- [_How I did it_](#how-i-did-it)
- [_Continued development_](#continued-development)
  - [_Useful resources_](#useful-resources)
- [_Author_](#author)
- [Acknowledgments](#acknowledgments)
## _Overview_
This notification app has been coded using Spring Boot, Spring JPA, Spring AWS SDK, MapStruct, Jackson,
H2 DB.
- src|
    - main
    - java|
      - com/xxx/challenge/notification|
        - config
        - dto
        - entity
        - mapper
        - payload
        - repository
        - services
          - exceptions
         
        - resources
          - db.migration
      - test 

## _Screenshot_
[![](./notification.png)]()
## _Links_
- Live Site URL: [http://127.0.0.1:8095/swagger-ui/index.html] 
## _Built with_

[![My Skills](https://skillicons.dev/icons?i=java,spring,redhat,aws,idea,git,github)](https://skillicons.dev)



 ## _How I did it_
```java
package com.xxxx.challenge.notification.config;
/*
 * Creating Queues  and Bindings
 */

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

``` 

## _Continued development_
- maybe....
### _Useful resources_
- [https://spring.io] Awesome Java framework!.
- [https://start.spring.io/]  Handy startup tool.
- [https://mvnrepository.com] Tools that help tackle the beast
## _Author_
- Website - [https://ferreiras.dev.br] 
- 
_Acknowledgments_
- 
