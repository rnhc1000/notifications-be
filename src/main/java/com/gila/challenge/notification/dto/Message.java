package com.gila.challenge.notification.dto;

import com.gila.challenge.notification.entity.User;
import lombok.*;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message{
  private String message;
  private String name;
  private String phone;
  private String email;
  private Instant createdAt;
  private String messageId;
}

