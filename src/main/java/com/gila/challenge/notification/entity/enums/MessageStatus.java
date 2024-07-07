package com.gila.challenge.notification.entity.enums;

public enum MessageStatus {
  DELIVERED_SMS(1),
  DELIVERED_EMAIL(2),
  READY_TO_DELIVER(3),
  WAITING_EXCHANGE(4);

  private final int codeStatus;

  private MessageStatus(int codeStatus) {
    this.codeStatus = codeStatus;
  }

  public int getCodeStatus() {
    return codeStatus;
  }

  public static MessageStatus valueOf(int codeStatus) {
    for (MessageStatus value : MessageStatus.values()) {
      if (value.getCodeStatus() == codeStatus) {
        return value;
      }
    }

    throw new IllegalArgumentException("Invalid MessageStatus code");
  }
}
