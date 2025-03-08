package com.gila.challenge.notification.entity;

import com.gila.challenge.notification.entity.enums.MessageStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Entity
@Table(name = "tb_messages")
public class Message {

  @Setter
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long messageId;

  @Setter
  @Getter
  @NotNull
  @Size(min = 1, max = 160)
  private String message;

  @Setter
  @Getter
  private String sender;

  @Setter
  @Getter
  private String email;

  @Setter
  @Getter
  private String phone;

  @Setter
  @Getter
  @CreationTimestamp
  private Instant createdAt;

  private Integer messageStatus = MessageStatus.READY_TO_DELIVER.getCodeStatus();

  @Setter
  @Getter
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  public Message(
      Long messageId, String message,
      String sender, String phone,
      String email,
      MessageStatus messageStatus, Instant createdAt) {
    this.messageId = messageId;
    this.message = message;
    this.sender = sender;
    this.phone = phone;
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


  public Message() {
  }

  @Override
  public String toString() {
    return "Message{" +
           "messageId=" + messageId +
           ", message='" + message + '\'' +
           ", sender='" + sender + '\'' +
           ", phone='" + phone + '\'' +
           ", email='" + email + '\'' +
           ", createdAt=" + createdAt +
           '}';
  }

}
