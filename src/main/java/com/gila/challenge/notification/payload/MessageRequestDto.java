package com.gila.challenge.notification.payload;

import com.gila.challenge.notification.entity.Message;
import lombok.Getter;

@Getter
public class MessageRequestDto {

  private String message;
  private String sender;
  private String phone;
  private String email;

  public MessageRequestDto() {
  }

  public MessageRequestDto(String message, String sender, String phone, String email) {
    this.message = message;
    this.sender = sender;
    this.phone = phone;
    this.email = email;
  }

  public MessageRequestDto(Message entity) {
    message = entity.getMessages();
    sender = entity.getSender();
    phone = entity.getPhone();
    email = entity.getEmail();
  }

  @Override
  public String toString() {
    return "MessageRequestDto{" +
           "message='" + message + '\'' +
           ", sender='" + sender + '\'' +
           ", phone='" + phone + '\'' +
           ", email='" + email + '\'' +
           '}';
  }
}




