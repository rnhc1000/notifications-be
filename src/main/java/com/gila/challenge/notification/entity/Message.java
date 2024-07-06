package com.gila.challenge.notification.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;


@Entity
@Table (name="tb_messages")
public class Message {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long messageId;

  @NotNull
  @Size (min=1, max=160)
  private String message;

  private String sender;
  private String phone;

  @ManyToOne(cascade={CascadeType.ALL})
  @JoinColumn(name="user_id")
  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private String email;
  public Message(Long messageId, Instant createdAt) {
    this.messageId = messageId;
    this.createdAt = createdAt;
  }

  public Message() {
  }

  @CreationTimestamp
  private Instant createdAt;

  public Long getMessageId() {
    return messageId;
  }

  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }



  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Instant getCreatedAt() {
    return createdAt;
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
            ", user=" + user +
            ", email='" + email + '\'' +
            ", createdAt=" + createdAt +
            '}';
  }
}
