package com.gila.challenge.notification.payload;

import com.gila.challenge.notification.entity.Message;

import java.time.Instant;

public class MessageResponseDto {

  private final Long messageId;
  private final String message;
  private final String sender;
  private final String phone;
  private final String email;
  private final Instant createdAt;
  private final Integer status;

  public MessageResponseDto(Long messageId, String message,
                            String sender, String phone, String email,
                            Instant createdAt, Integer status) {
    this.messageId = messageId;
    this.message = message;
    this.sender = sender;
    this.phone = phone;
    this.email = email;
    this.createdAt = createdAt;
    this.status = status;
  }

  public MessageResponseDto(Message entity) {
    messageId = entity.getMessageId();
    message = entity.getMessages();
    sender = entity.getSender();
    phone = entity.getPhone();
    email = entity.getEmail();
    createdAt = entity.getCreatedAt();
    status = entity.getMessageStatus().getCodeStatus();

  }

  public Long getMessageId() {
    return messageId;
  }

  public String getMessage() {
    return message;
  }

  public String getSender() {
    return sender;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Integer getStatus() {
    return status;
  }
}

