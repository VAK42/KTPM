package com.vak.oop.service;

import com.vak.oop.model.Export;
import com.vak.oop.repository.ExportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportService {
  private final ExportRepository exportRepository;

  public ExportService(ExportRepository exportRepository) {
    this.exportRepository = exportRepository;
  }

  public List<Export> getAllExports() {
    return exportRepository.findAll();
  }

  public List<Export> saveAll(List<Export> exports) {
    return exportRepository.saveAll(exports);
  }
}