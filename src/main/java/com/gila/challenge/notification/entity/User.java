package com.gila.challenge.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


@Getter
@Entity
@Table (name = "tb_users",
        uniqueConstraints = {

                @UniqueConstraint (columnNames = "userPhone")
        })
public class User implements Serializable {
  private static final long serialVersionUUID = 1L;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long userId;

  @NotBlank
  @Size (min = 4, max = 30)
  private String userName;

  @NotBlank
  @Email
  @Size (min = 8, max = 40)
  private String userEmail;

  @NotBlank
  @Size (min = 6, max = 20)
  private String userPhone;

  private Integer countMessages = 1;

  @JsonIgnore
  @OneToMany
  @JoinColumn(name = "user_id")
  private List<Message> messages = new LinkedList<>();

  public User(
          Long userId,
          String userName,
          String userEmail,
          String userPhone,
          Integer countMessages
  ) {
    this.userId = userId;
    this.userName = userName;
    this.userEmail = userEmail;
    this.userPhone = userPhone;
    this.countMessages = countMessages;
  }

  public User(String userName, String userEmail, String userPhone, Integer countMessages) {
    this.userName = userName;
    this.userEmail = userEmail;
    this.userPhone = userPhone;
    this.countMessages = countMessages;
  }

  public User(String userName, String userEmail, String userPhone) {
    this.userName = userName;
    this.userEmail = userEmail;
    this.userPhone = userPhone;
  }

  public User() {
  }

  public User(Long id) {
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public void setCountMessages(Integer countMessages) {
    this.countMessages = countMessages;
  }

  @Override
  public String toString() {
    return "User{" +
           "userId=" + userId +
           ", userName='" + userName + '\'' +
           ", userEmail='" + userEmail + '\'' +
           ", userPhone='" + userPhone + '\'' +
           ", countMessages=" + countMessages +
           '}';
  }
}
