package com.gila.challenge.notification.service.exceptions;

public class ForbiddenException extends RuntimeException {

  public ForbiddenException(String msg) {
    super(msg);
  }
}
