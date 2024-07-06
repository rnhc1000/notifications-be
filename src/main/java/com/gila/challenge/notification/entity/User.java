package com.gila.challenge.notification.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table (name = "tb_users",
        uniqueConstraints = {
                @UniqueConstraint (columnNames = "name"),
                @UniqueConstraint (columnNames = "email"),
                @UniqueConstraint (columnNames = "phone")
        })
public class User {
  public User(Long user_id, String name, String email, String phone) {
    this.user_id = user_id;
    this.userName = name;
    this.userEmail = email;
    this.userPhone = phone;
  }

  public User(String userName, String userEmail, String userPhone) {
    this.userName = userName;
    this.userEmail = userEmail;
    this.userPhone = userPhone;
  }

  public User() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long user_id;

  @NotBlank
  @Column (unique = true)
  @Size (min = 4, max = 20)
  private String userName;

  @NotBlank
  @Email
  @Size (min = 8, max = 40)
  private String userEmail;

  @NotBlank
  @Size (min = 6, max = 20)
  private String userPhone;


  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  @Override
  public String toString() {
    return "User{" +
            "user_id=" + user_id +
            ", userName='" + userName + '\'' +
            ", userEmail='" + userEmail + '\'' +
            ", userPhone='" + userPhone + '\'' +
            '}';
  }
}
