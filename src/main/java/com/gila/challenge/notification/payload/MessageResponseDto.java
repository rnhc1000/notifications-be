package com.gila.challenge.notification.payload;

import java.time.Instant;

public record MessageResponseDto(
        Long messageId, String message,
        String sender, String phone,
        String email, Instant createdAt,
        Integer status) {
  public Long getMessageId() {
    return messageId;
  }
}
