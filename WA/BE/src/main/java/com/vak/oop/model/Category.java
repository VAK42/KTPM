package com.vak.oop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  @Id
  @GeneratedValue
  @Column(name = "categoryid", columnDefinition = "UUID")
  private UUID id;
  @Column(name = "name", nullable = false, unique = true, length = 50)
  private String name;
}