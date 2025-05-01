package com.vak.oop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"")
public class UserEntity {
  @Id
  @Column(name = "\"userid\"")
  private Long userid;
  @Column(name = "\"username\"")
  private String username;
  @Column(name = "\"password\"")
  private String password;
  @Column(name = "\"email\"")
  private String email;
  @Column(name = "\"role\"")
  private String role;

  public String getUsername() {
    return username;
  }
}