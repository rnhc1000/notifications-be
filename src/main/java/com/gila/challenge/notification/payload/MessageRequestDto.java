package com.gila.challenge.notification.payload;

import com.gila.challenge.notification.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;


@Getter
public class MessageRequestDto implements Serializable {
  private String message;
  private String sender;
  private String phone;
  private String email;
  private Long userId;

  public MessageRequestDto(String message, String sender, String phone, String email, Long userId, User user) {
    this.message = message;
    this.sender = sender;
    this.phone = phone;
    this.email = email;
    this.userId = userId;
    this.user = user;
  }

  public MessageRequestDto(String message, String sender, String phone, String email, User user) {
    this.message = message;
    this.sender = sender;
    this.phone = phone;
    this.email = email;
    this.user = user;
  }

  public MessageRequestDto() {
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User user;
}
