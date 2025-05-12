package com.vak.oop.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "\"user\"")
public class UserEntity {
  @Id
  @Column(name = "userid")
  private UUID userId;
  @Column(name = "username", nullable = false, unique = true)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "role", nullable = false)
  private String role;

  public void setUsername(String username) {
    this.username = username;
  }

  public UUID getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }
}