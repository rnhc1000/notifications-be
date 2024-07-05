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
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  public User() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long user_id;

  @NotBlank
  @Column (unique = true)
  @Size (min = 4, max = 20)
  private String name;

  @NotBlank
  @Email
  @Size (min = 8, max = 40)
  private String email;

  @NotBlank
  @Size (min = 6, max = 20)
  private String phone;

  public User(Long id) {
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


}
