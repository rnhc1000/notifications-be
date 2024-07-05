package com.gila.challenge.notification.controller;

import com.gila.challenge.notification.entity.User;
import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import com.gila.challenge.notification.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class MessageController {

  @Autowired
  private MessageService messageService;


  //  public String saveMessage(@RequestBody Message message) {
//    message.setMessageId(UUID.randomUUID().toString());
//    MessageEvent event = new MessageEvent();
//    event.setName("Ricardo Ferreira");
//    event.setEmail("ricardo@ferreiras.dev.br");
//    event.setPhone("+5571993005555");
//    event.setAdvice("The message is about to be consumed...");
//    event.setMessage(message);
//    messageService.forwardMessage(event);
//    return "Message sent to RabbitMQ!";
//  }
  @PostMapping (value = "/messages")
  public ResponseEntity persistMessage(@RequestBody MessageRequestDto messageRequestDto, User user) {

    MessageResponseDto messageResponseDto = messageService.persist(messageRequestDto,user);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("{messageId}")
            .buildAndExpand(messageResponseDto.getMessageId())
            .toUri()
    ).body(messageResponseDto);

  }

  @GetMapping (value = "/messages")
  public ResponseEntity<List<MessageResponseDto>> getMessage() {

    return ResponseEntity.ok(messageService.getMessage());
  }


  @GetMapping (value = "/messages/{messageId}")
  public ResponseEntity<MessageResponseDto> findById(@PathVariable Long messageId) {

    MessageResponseDto messageResponseDto = messageService.getMessageById(messageId);

    return ResponseEntity.ok(messageResponseDto);
  }

}
