package com.gila.challenge.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageEvent {

  private String name;
  private String phone;
  private String email;
  private String advice;
  public Message message;

}

