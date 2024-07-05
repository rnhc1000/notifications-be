package com.gila.challenge.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageError {
  private Instant timestamp;
  private Integer status;
  private String error;
  private String path;
}
