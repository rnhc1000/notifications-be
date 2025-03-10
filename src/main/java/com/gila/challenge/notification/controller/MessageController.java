package com.gila.challenge.notification.controller;

import com.gila.challenge.notification.payload.MessageRequestDto;
import com.gila.challenge.notification.payload.MessageResponseDto;
import com.gila.challenge.notification.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class MessageController {

  public static final Logger logger = LoggerFactory.getLogger(MessageController.class);

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @Operation(summary = "Post a messages",
      responses = {
          @ApiResponse(responseCode = "201", description = "Message Created.",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = MessageRequestDto.class))})
      })
  @PostMapping("/messages")
  public ResponseEntity<MessageResponseDto> persistMessage(@RequestBody MessageRequestDto messageRequestDto) {

    logger.info("::: MessageRequestDTo@controller: {} :::", messageRequestDto);

    MessageResponseDto messageResponseDto = messageService.persist(messageRequestDto);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{messageId}")
            .buildAndExpand(messageResponseDto.getMessageId())
            .toUri())
        .body(messageResponseDto);
  }


  @Operation(summary = "Fetch all messages available",
      responses = {
          @ApiResponse(responseCode = "200", description = "Get all messages, even none.",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = MessageController.class))})
      })
  @GetMapping(value = "/messages")
  public ResponseEntity<List<MessageResponseDto>> getMessage() {

    return ResponseEntity.ok(messageService.getMessage());
  }

  @Operation(summary = "Fetch 5 messages per page",
      description = "Fetch paginated messages",
      responses = {
          @ApiResponse(responseCode = "200",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = MessageController.class))})
      })
  @GetMapping(value = "/pagedMessages")
  public ResponseEntity<Page<MessageResponseDto>> getAllMessages(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "4") int size,
      Pageable pageable
  ) {
    logger.info(" ::: Page Number -> , Size of Each Page -> ,{}, {}", page, size);

    Page<MessageResponseDto> dto = messageService.getPagedMessages(page, size, pageable);

    return ResponseEntity.ok(dto);
  }

  @Operation(summary = "Get a messages by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Got the messages requested by its id",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = MessageController.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Message not found",
          content = @Content)})
  @ResponseStatus
  @GetMapping(value = "/messages/{messageId}")
  public ResponseEntity<MessageResponseDto> findById(@Parameter(description = "messages id to be fetched") @PathVariable Long messageId) {

    MessageResponseDto messageResponseDto = messageService.getMessageById(messageId);

    return ResponseEntity.ok(messageResponseDto);
  }
}
