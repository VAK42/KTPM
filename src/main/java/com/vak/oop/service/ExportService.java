package com.vak.oop.service;

import com.vak.oop.dao.ExportDao;
import com.vak.oop.model.ExportEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ExportService {
  private final ExportDao exportDao;

  public ExportService(EntityManager entityManager) {
    this.exportDao = new ExportDao(entityManager);
  }

  public List<ExportEntity> getAllExports() {
    return exportDao.getAllExports();
  }

  public List<ExportEntity> getExportsByPage(int page, int size) {
    return exportDao.getExportsByPage(page, size);
  }

  public int getTotalExportCount() {
    return exportDao.getTotalExportCount();
  }
}