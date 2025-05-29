package com.vak.oop.service;

import com.vak.oop.dao.ImportDao;
import com.vak.oop.model.ImportEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ImportService {
  private final ImportDao importDao;

  public ImportService(EntityManager entityManager) {
    this.importDao = new ImportDao(entityManager);
  }

  public List<ImportEntity> getAllImports() {
    return importDao.getAllImports();
  }

  public void saveImport(ImportEntity importEntity) {
    importDao.saveImport(importEntity);
  }

  public List<ImportEntity> getImportsByPage(int page, int size) {
    return importDao.getImportsByPage(page, size);
  }

  public int getTotalImportCount() {
    return importDao.getTotalImportCount();
  }
}