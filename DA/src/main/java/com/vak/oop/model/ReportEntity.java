package com.vak.oop.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "report")
public class ReportEntity {
  @Id
  @GeneratedValue
  @Column(name = "reportid")
  private UUID reportid;
  @ManyToOne
  @JoinColumn(name = "userid", nullable = false)
  private UserEntity user;
  @Column(name = "rpname")
  private String rpname;
  @Column(name = "rpinfo")
  private String rpinfo;

  public UUID getReportid() {
    return reportid;
  }

  public UserEntity getUser() {
    return user;
  }

  public String getRpname() {
    return rpname;
  }

  public String getRpinfo() {
    return rpinfo;
  }
}