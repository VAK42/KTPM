package com.vak.oop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue
  @Column(name = "userid", columnDefinition = "UUID")
  private UUID id;
  @Column(name = "username", nullable = false, unique = true, length = 50)
  private String username;
  @Column(name = "password", nullable = false, length = 255)
  private String password;
  @Column(name = "email", nullable = false, unique = true, length = 100)
  private String email;
  @Column(name = "role", nullable = false, length = 20)
  private String role;
}