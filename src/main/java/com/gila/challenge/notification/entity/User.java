package com.gila.challenge.notification.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table (name = "tb_users",
        uniqueConstraints = {
                @UniqueConstraint (columnNames = "name"),
                @UniqueConstraint (columnNames = "email"),
                @UniqueConstraint (columnNames = "phone")
        })
public class User {
  public User(Long user_Id, String name, String email, String phone) {
    this.user_Id = user_Id;
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  public User() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long user_Id;

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

  public Long getUser_Id() {
    return user_Id;
  }

  public void setUser_Id(Long user_Id) {
    this.user_Id = user_Id;
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
