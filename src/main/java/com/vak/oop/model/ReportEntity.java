package com.vak.oop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class ReportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reportid")
  private Long reportid;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userid", nullable = false)
  private UserEntity user;
  @Column(name = "rpname")
  private String rpname;
  @Column(name = "rpinfo")
  private String rpinfo;

  public Long getReportid() {
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