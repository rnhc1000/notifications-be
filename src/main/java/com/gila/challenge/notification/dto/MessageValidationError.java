package com.gila.challenge.notification.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MessageValidationError extends MessageError{
  private final List<FieldMessage> errors = new ArrayList<>();

  public MessageValidationError(Instant timestamp, Integer status, String error, String path) {
    super(timestamp, status, error, path);
  }

  public List<FieldMessage> getErrors() {
    return errors;
  }

  public void addError(String fieldName, String message) {
    errors.removeIf(x -> x.getFieldName().equals(fieldName));
    errors.add(new FieldMessage(fieldName, message));
  }
}
