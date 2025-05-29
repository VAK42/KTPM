package com.vak.oop.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "category")
public class CategoryEntity {
  @Id
  @Column(name = "categoryid")
  private UUID categoryId;
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}