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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

  private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

  private final NotificationRabbitService notificationRabbitService;
  private final MessageRepository messageRepository;
  private final String exchange;
  private final UserService userService;

  public MessageService(NotificationRabbitService notificationRabbitService,
                        MessageRepository messageRepository,
                        @Value("${rabbitmq.exchange.message.name}") String exchange, UserService userService
  ) {
    this.notificationRabbitService = notificationRabbitService;
    this.messageRepository = messageRepository;
    this.exchange = exchange;
    this.userService = userService;
  }

  @Transactional
  public MessageResponseDto persist(MessageRequestDto messageRequestDto) {
    String email;
    String userPhone;
    String name;

    Message message = MapperMessages.INSTANCE.dtoToMessage(messageRequestDto);

    logger.info("::: Message -> {} :::", message);

    userPhone = message.getPhone();
    name = message.getSender();
    email = message.getEmail();

    userService.saveUser(name, email, userPhone);

    logger.info("::: name, email, phone, {}, {}, {} :::", name, email, userPhone);

    logger.info("::: message: -> {} :::", message);

    try {
      messageRepository.save(message);
    } catch (DatabaseException dex) {
      throw new DatabaseException("Error persisting Message Entity... User already exists..!");
    }

    logger.info("::: User data inserted... :::");
    logger.info("::: Let's start notifying the subscribers! :::");
    notifyRabbitMq(message);

    return MapperMessages.INSTANCE.messageToDto(message);
  }

  private void notifyRabbitMq(Message message) {

    notificationRabbitService.notify(message, "message.routingKey", exchange);
  }

  @Transactional(readOnly = true)
  public List<MessageResponseDto> getMessage() {

    Iterable<Message> messages = messageRepository.findAll();

    return MapperMessages.INSTANCE.convertListEntityToListDto(messages);
  }

  @Transactional(readOnly = true)
  public MessageResponseDto getMessageById(Long messageId) {

    Message message = messageRepository.findById(messageId).orElseThrow(
        () -> new ResourceNotFoundException("Resource not found!"));

    return MapperMessages.INSTANCE.messageToDto(message);
  }

  @Transactional
  public ResponseEntity<Map<String, Object>> getPagedMessages(int page, int size) {

    try {

      Pageable paging = PageRequest.of(page, size);
      Page<Message> pageMessages;
      pageMessages = messageRepository.findAll(paging);
      Iterable<Message> messages = pageMessages.getContent();

      Map<String, Object> response = new LinkedHashMap<>();
      response.put("messages", MapperMessages.INSTANCE.convertListEntityToListDto(messages));
      response.put("currentPage", pageMessages.getNumber());
      response.put("totalItems", pageMessages.getTotalElements());
      response.put("totalPages", pageMessages.getTotalPages());
      response.put("size", pageMessages.getSize());
      logger.info("::: Response: -> {} :::", response);

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (DatabaseException ex) {

      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
