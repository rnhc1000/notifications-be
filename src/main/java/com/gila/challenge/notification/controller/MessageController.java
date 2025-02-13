package com.gila.challenge.notification.controller;

import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import com.gila.challenge.notification.repository.MessageRepository;
import com.gila.challenge.notification.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

  public final static Logger logger = LoggerFactory.getLogger(MessageService.class);

  @Autowired
  private MessageService messageService;


  @Autowired
  private MessageRepository messageRepository;


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
  @Operation (summary = "Post a messages")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "201", description = "Message Created.",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = MessageController.class))})})
  @ResponseStatus
  @PostMapping (value = "/messages")
  public ResponseEntity persistMessage(@RequestBody MessageRequestDto messageRequestDto) {

    MessageResponseDto messageResponseDto = messageService.persist(messageRequestDto);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                                                             .path("/{messageId}")
                                                             .buildAndExpand(messageResponseDto.getMessageId())
                                                             .toUri())
                         .body(messageResponseDto);

  }

  @Operation (summary = "Fetch all messages available")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get all messages, even none.",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = MessageController.class))})})
  @ResponseStatus
  @GetMapping (value = "/messages")
  public ResponseEntity<List<MessageResponseDto>> getMessage() {

    return ResponseEntity.ok(messageService.getMessage());
  }

  @Operation (summary = "Fetch 5 messages per page")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Get up to 12 messages per page.",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = MessageController.class))})})
  @ResponseStatus
  @GetMapping (value = "/pagedMessages")
  public ResponseEntity<Map<String, Object>> getAllMessages(
          @RequestParam (defaultValue = "0") int page,
          @RequestParam (defaultValue = "5") int size
  ) {
    logger.info(String.format(("Page Number -> , Size of Each Page -> , %s, %s"), page, size));
    return messageService.getPagedMessages(page, size);
  }

  @Operation (summary = "Get a message by its id")
  @ApiResponses (value = {
          @ApiResponse (responseCode = "200", description = "Got the message requested by its id",
                  content = {@Content (mediaType = "application/json",
                          schema = @Schema (implementation = MessageController.class))}),
          @ApiResponse (responseCode = "400", description = "Invalid id supplied",
                  content = @Content),
          @ApiResponse (responseCode = "404", description = "Message not found",
                  content = @Content)})
  @ResponseStatus
  @GetMapping (value = "/messages/{messageId}")
  public ResponseEntity<MessageResponseDto> findById(@Parameter (description = "message id to be fetched") @PathVariable Long messageId) {

    MessageResponseDto messageResponseDto = messageService.getMessageById(messageId);

    return ResponseEntity.ok(messageResponseDto);
  }
}
