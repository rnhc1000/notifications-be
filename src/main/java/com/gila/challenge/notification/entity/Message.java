package com.gila.challenge.notification.entity;

import com.gila.challenge.notification.entity.enums.MessageStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Entity
@Table (name = "tb_messages")
public class Message implements Serializable {

  private static final long serialVersionUUID = 1L;

  @Getter
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long messageId;

  @Getter
  @NotNull
  @Size (min = 1, max = 160)
  private String message;

  @Getter
  private String sender;

  @Getter
  private String email;

  @Getter
  private String phone;

  @Getter
  @CreationTimestamp
  private Instant createdAt;

  private Integer messageStatus = MessageStatus.READY_TO_DELIVER.getCodeStatus();

  @Getter
  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn (name = "user_id")
  private User userId;

  public Message(
          Long messageId, String message,
          String sender, String phone,
          User userId, String email,
          MessageStatus messageStatus, Instant createdAt) {
    this.messageId = messageId;
    this.message = message;
    this.sender = sender;
    this.phone = phone;
    this.userId = userId;
    this.email = email;
    this.createdAt = createdAt;
    setMessageStatus(messageStatus);
  }

  public MessageStatus getMessageStatus() {
    return MessageStatus.valueOf(messageStatus);
  }

  public void setMessageStatus(MessageStatus messageStatus) {
    if (messageStatus != null) {
      this.messageStatus = messageStatus.getCodeStatus();
    }
  }

  public void setUserId(User userId) {
    this.userId = userId;
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

  public Message(Long messageId, Instant createdAt, User userId) {
    this.messageId = messageId;
    this.createdAt = createdAt;
    this.userId = userId;
  }

  public Message() {
  }

  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Message{" +
           "messageId=" + messageId +
           ", message='" + message + '\'' +
           ", sender='" + sender + '\'' +
           ", phone='" + phone + '\'' +
           ", userId=" + userId +
           ", email='" + email + '\'' +
           ", createdAt=" + createdAt +
           '}';
  }

}
