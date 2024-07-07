package com.gila.challenge.notification.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Data
public  class UserRequestDto {
  private  String nameUser;
  private  String phoneUser;
  private  String emailUser;
  private  Integer messagesCount;


}
