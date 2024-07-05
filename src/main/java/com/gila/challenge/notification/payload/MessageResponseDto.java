package com.gila.challenge.notification.payload;

import com.gila.challenge.notification.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponseDto implements Serializable {
  private Long messageId;
  private String message;
  private String sender;
  private String phone;
  private String email;
  private Instant createdAt;
}
