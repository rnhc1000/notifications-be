package com.gila.challenge.notification.payload;

import lombok.Getter;

import java.time.Instant;
import java.util.Map;

public record MessageResponseDto (
   Long messageId,   String message,
   String sender,   String phone,
   String email,   Instant createdAt,
   Integer status) {
  public Long getMessageId() {
    return messageId;
  }
}
