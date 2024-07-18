package com.gila.challenge.notification.payload;

import com.gila.challenge.notification.entity.User;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class MessageResponseDto implements Serializable {
  private Long messageId;
  private String message;
  private String sender;
  private String phone;
  private String email;
  private Instant createdAt;
  private Integer status;

  public Long getMessageId() {
    return messageId;
  }
}
