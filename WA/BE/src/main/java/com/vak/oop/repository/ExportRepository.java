package com.vak.oop.repository;

import com.vak.oop.model.Export;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportRepository extends JpaRepository<Export, Long> {
}