package com.gila.challenge.notification.service;

import com.gila.challenge.notification.entity.Message;
import com.gila.challenge.notification.entity.enums.MessageStatus;
import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import com.gila.challenge.notification.repository.MessageRepository;
import com.gila.challenge.notification.service.exceptions.DatabaseException;
import com.gila.challenge.notification.service.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class MessageService {

  private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

  private final NotificationRabbitService notificationRabbitService;
  private final MessageRepository messageRepository;
  private final String exchange;
  private final UserService userService;

  public MessageService(NotificationRabbitService notificationRabbitService,
                        MessageRepository messageRepository,
                        @Value("${rabbitmq.exchange.messages.name}") String exchange, UserService userService
  ) {
    this.notificationRabbitService = notificationRabbitService;
    this.messageRepository = messageRepository;
    this.exchange = exchange;
    this.userService = userService;
  }

  @Transactional
  public MessageResponseDto persist(MessageRequestDto messageRequestDto) {
//    String email;
//    String userPhone;
//    String name;
    logger.info("::: MessageRequestDto -> {} :::", messageRequestDto);

//    Message message = MapperMessages.INSTANCE.dtoToMessage(messageRequestDto);

    Message message = new Message(null, messageRequestDto.getMessage(), messageRequestDto.getSender(),
        messageRequestDto.getPhone(), messageRequestDto.getEmail(), MessageStatus.READY_TO_DELIVER,
        Instant.now());

    logger.info("::: Message -> {} :::", message);

    try {
      logger.info("::: Trying to persist in the DB :::");
      Message persisted = messageRepository.save(message);
      logger.info("::: Persistence OK! {} :::", persisted);

    } catch (DatabaseException dex) {
      throw new DatabaseException("Error persisting Message Entity... User already exists..!");
    }

    logger.info("::: Let's start notifying the subscribers! :::");
    notifyRabbitMq(message);

    return new MessageResponseDto(message);
  }

  private void notifyRabbitMq(Message message) {

    notificationRabbitService.notify(message, "messages.routingKey", exchange);
  }

  @Transactional(readOnly = true)
  public List<MessageResponseDto> getMessage() {

    List<Message> messages = messageRepository.findAll();

    return messages.stream().map(MessageResponseDto::new).toList();
  }

  @Transactional(readOnly = true)
  public MessageResponseDto getMessageById(Long messageId) {

    Message message = messageRepository.findById(messageId).orElseThrow(
        () -> new ResourceNotFoundException("Resource not found!"));

    return new MessageResponseDto(message);
  }

  @Transactional(readOnly = true)
  public Page<MessageResponseDto> getPagedMessages(int page, int size, Pageable pageable) {

    Page<Message> messages = messageRepository.findAll(pageable);

    return messages.map(MessageResponseDto::new);
  }
}

