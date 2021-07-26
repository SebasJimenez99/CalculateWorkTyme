/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service.implement;

import com.ias.project.backend.model.Report;
import com.ias.project.backend.repository.ReportRepository;
import com.ias.project.backend.service.ReportService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastian
 */
@Service
@Slf4j
public class ReportServiceImp implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Report createReport(Report report) {
        log.info("Entry into service function to create a new report");
        Report newReport = new Report();
        if (report.getIdService() != null && report.getIdTechnician() != null
                && report.getInitialDate() != null 
                && report.getFinalDate() != null) {
            newReport = reportRepository.save(report);
        }
        return newReport;
    }

    @Override
    public Report updateReport(Integer id, Report report) {
        log.info("Entry into service function to update a report");
        Optional<Report> newReport = reportRepository.findById(id);
        if (!newReport.isPresent()){
            return null;
        }
        reportRepository.save(report);
        return report;
    }

    @Override
    public List<Report> findAllReports() {
        List<Report> listReport = reportRepository.findAll();
        return listReport;
    }

    @Override
    public Boolean deleteReport(Integer reportId) {
        Optional<Report> reportDelete = reportRepository
                .findById(reportId);
        if(!reportDelete.isPresent()){
            return false;
        }
        reportRepository.deleteById(reportDelete.get().getId());
        return true;
    }

}
