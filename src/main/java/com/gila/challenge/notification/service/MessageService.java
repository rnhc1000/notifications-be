package com.gila.challenge.notification.service;

import com.gila.challenge.notification.entity.Message;
import com.gila.challenge.notification.entity.User;
import com.gila.challenge.notification.mapper.MapperMessages;
import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import com.gila.challenge.notification.repository.MessageRepository;
import com.gila.challenge.notification.repository.UserRepository;
import com.gila.challenge.notification.service.exceptions.DatabaseException;
import com.gila.challenge.notification.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {
  @Autowired
  private final NotificationRabbitService notificationRabbitService;
  @Autowired
  private final MessageRepository messageRepository;

  private User user;

  private final String exchange;
  //  private final User user;
  @Autowired
  private UserRepository userRepository;

  public MessageService(NotificationRabbitService notificationRabbitService,
                        MessageRepository messageRepository,
//                        User user,
                        @Value ("${rabbitmq.exchange.message.name}") String exchange
  ) {
    this.notificationRabbitService = notificationRabbitService;
    this.messageRepository = messageRepository;
    this.exchange = exchange;
//    this.user = user;

  }

  final static Logger logger = LoggerFactory.getLogger(MessageService.class);

  @Transactional
  public MessageResponseDto persist(MessageRequestDto messageRequestDto) {
    String email, phone, name;

    Message message = MapperMessages.INSTANCE.dtoToMessage(messageRequestDto);
    phone = message.getPhone();
    name = message.getSender();
    email = message.getEmail();
    logger.info(String.format(("name, email, phone, %s, %s, %s"), name, email, phone));
    user = new User(name, email, phone);
    System.out.println(user);
//    Long id = user.getUser_id();
    if (userRepository.existsById(user.getUser_id())) {
      message.setUser(user);
      messageRepository.save(message);
    } else {
      logger.info(String.format(("name, email, phone, %s, %s, %s"), name, email, phone));
      message.setUser(user);
      try {
        userRepository.save(user);
        messageRepository.save(message);
      } catch (EntityNotFoundException e) {
        logger.info("Database error access");
        logger.info("Value not returned!!!");
        throw new DatabaseException("Error persisting data...");
      }
    }

//    logger.info(String.format(("email, name, phone -  %s, %s, %s"), email, name, phone));
//    message.setUser(new User(email,name, phone));
    logger.info("User data inserted...");

    notifyRabbitMq(message);

    return MapperMessages.INSTANCE.messageToDto(message);
  }

  //  @Transactional
//  public User recoverUserId(User user) {
//    return userRepository.findById(user.getUser_id());
//  }
  private void notifyRabbitMq(Message message) {


    notificationRabbitService.notify(message, "message.routingKey", exchange);

  }

  @Transactional (readOnly = true)
  public List<MessageResponseDto> getMessage() {

    Iterable<Message> messages = messageRepository.findAll();
    return MapperMessages.INSTANCE.convertListEntityToListDto(messages);

  }

  @Transactional (readOnly = true)
  public MessageResponseDto getMessageById(Long messageId) {

    Message message = messageRepository.findById(messageId).orElseThrow(
            () -> new ResourceNotFoundException("Resource not found!"));
    return MapperMessages.INSTANCE.messageToDto(message);
  }

}
