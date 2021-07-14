/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service.implement;

import com.ias.project.backend.model.Report;
import com.ias.project.backend.repository.ReportRepository;
import com.ias.project.backend.service.CalculateTimeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastian
 */
@Service
public class CalculateTimeServiceImp implements CalculateTimeService {

    @Autowired
    private ReportRepository reportRepository;

    public Long hoursOfOperation(String idTechnician, Integer weekNumber) {
        List<Report> listReports = reportRepository.findAll();
        List<Report> reportsByTechnician = getTechnicianById(listReports, 
                idTechnician);
    }
    
    private List<Report> getTechnicianById(List<Report> listReports,
            String idTechnician) {
        List<Report> reportsByTechnician = new ArrayList<>();
        listReports.stream().filter((report) -> 
                (report.getIdTechnician().equals(idTechnician)))
                .forEachOrdered((report) -> {
            reportsByTechnician.add(report);
        });
        return reportsByTechnician;
    }
}
