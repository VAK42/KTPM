package com.vak.oop.service;

import com.vak.oop.dao.ReportDao;
import com.vak.oop.model.ReportEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ReportService {
    private final ReportDao reportDao;

    public ReportService(EntityManager entityManager) {
        this.reportDao = new ReportDao(entityManager);
    }

    public void deleteReport(ReportEntity reportEntity) {
        reportDao.deleteReport(reportEntity);
    }

    public List<ReportEntity> getReportsByPage(int page, int size) {
        return reportDao.getReportsByPage(page, size);
    }

    public int getTotalReportCount() {
        return reportDao.getTotalReportCount();
    }
}