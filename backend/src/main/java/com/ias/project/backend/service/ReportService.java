/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service;

import com.ias.project.backend.model.Report;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface ReportService {

    Report createReport(Report report);
    Report updateReport(Integer id, Report report);
    List<Report> findAllReports();
    Boolean deleteReport(Integer reportId);
}
