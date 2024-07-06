package com.gila.challenge.notification.payload;

import com.gila.challenge.notification.entity.User;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageRequestDto implements Serializable {
  private String message;
  private String sender;
  private String phone;
  private String email;
}




