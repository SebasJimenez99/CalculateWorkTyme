/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service.implement;

import com.ias.project.backend.model.Report;
import com.ias.project.backend.repository.ReportRepository;
import com.ias.project.backend.service.ReportService;
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
        Report newReport = new Report();
        if (report.getIdService() != null && report.getIdTechnician() != null
                && report.getInitialDate() != null 
                && report.getFinalDate() != null) {
            newReport = reportRepository.save(report);
        }
        return newReport;
    }
}
