package com.gila.challenge.notification.service;

import com.gila.challenge.notification.entity.Message;
import com.gila.challenge.notification.mapper.MapperMessages;
import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import com.gila.challenge.notification.repository.MessageRepository;
import com.gila.challenge.notification.service.exceptions.DatabaseException;
import com.gila.challenge.notification.service.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
  final static Logger logger = LoggerFactory.getLogger(MessageService.class);
  private final String exchange;
  @Autowired
  private final NotificationRabbitService notificationRabbitService;
  @Autowired
  private final MessageRepository messageRepository;

  @Autowired
  private UserService userService;

  public MessageService(NotificationRabbitService notificationRabbitService,
                        MessageRepository messageRepository,
                        @Value ("${rabbitmq.exchange.message.name}") String exchange
  ) {
    this.notificationRabbitService = notificationRabbitService;
    this.messageRepository = messageRepository;
    this.exchange = exchange;
  }


  @Transactional
  public MessageResponseDto persist(MessageRequestDto messageRequestDto) {
    String email, userPhone, name;

    Message message = MapperMessages.INSTANCE.dtoToMessage(messageRequestDto);


    System.out.println(message);
    userPhone = message.getPhone();
    name = message.getSender();
    email = message.getEmail();

    userService.saveUser(name, email, userPhone);

//     user = new User(name, email, userPhone);
    logger.info(String.format(("name, email, phone, %s, %s, %s"), name, email, userPhone));
//    Long id = userService.getId(userPhone);
//    message.setUserId(id);
//    logger.info(String.format(("Id ->  %s"), id));
    System.out.println(message);
//    boolean isUser = userService.userExists(userPhone);
//    if (isUser) {
//      logger.info(String.format(("User Exists? %s"), isUser));
    try {
//        Long id = userRepository.getUserId();
//       logger.info(String.format(("User id found -> %s"), id));
      messageRepository.save(message);
    } catch (DatabaseException dex) {
      throw new DatabaseException("Error persisting Message Entity... User already exists..!");
    }
//    } else {
//      try {
//        userService.persist(user);
//        messageRepository.save(message);
////    System.out.println(user);
////      message.setUser(user);
//        System.out.println(message);
//      } catch (DatabaseException dbx) {
//        throw new DatabaseException(("Error persisting User and Message Entities!"));
//      }

//    }


    logger.info("User data inserted...");
    logger.info("Let's start notifying the subscribers!");
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

  @Transactional
  public ResponseEntity<Map<String, Object>> getPagedMessages(int page, int size) {

    try {
      Iterable<Message> messages = new LinkedList<>();
      Pageable paging = PageRequest.of(page, size);
//      Message message = MapperMessages.INSTANCE.dtoToMessage(messageResponseDto);
      Page<Message> pageMessages;
      pageMessages = messageRepository.findAll(paging);
      messages = pageMessages.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("messages", MapperMessages.INSTANCE.convertListEntityToListDto(messages));
      response.put("currentPage", pageMessages.getNumber());
      response.put("totalItems", pageMessages.getTotalElements());
      response.put("totalPages", pageMessages.getTotalPages());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
