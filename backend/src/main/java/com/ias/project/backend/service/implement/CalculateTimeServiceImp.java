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
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
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
        List<Report> reportsByTechnicianAndWeekNumber =
                getTechnicianByWeekNumber(reportsByTechnician, weekNumber);
        List<Long> hoursOfOperation =
                getTotalHoursOfOperation(reportsByTechnicianAndWeekNumber);
        Long totalHoursOfOperation = 0L;
        totalHoursOfOperation = hoursOfOperation.stream().map((hours) -> hours)
                .reduce(totalHoursOfOperation, (accumulator, _item) -> 
                        accumulator + _item);
        return totalHoursOfOperation;
    }

    private List<Long> getTotalHoursOfOperation(
            List<Report> reportsByTechnicianAndWeekNumber) {
        List<Long> totalHoursOfOperation = new ArrayList<>();
        reportsByTechnicianAndWeekNumber.forEach((report) -> {
            Long initialTime = report.getInitialDate().getTime();
            Long finalTime = report.getFinalDate().getTime();
            Long mili = finalTime - initialTime;
            Long min = mili/(1000*60);
            Long hour = min/60;
            totalHoursOfOperation.add(hour);
        });
        return totalHoursOfOperation;
    }

    private List<Report> getTechnicianByWeekNumber(
            List<Report> reportsByTechnician, Integer weekNumber) {
        List<Report> reportsByTechnicianAndWeekNumber = new ArrayList<>();
        Integer numberWeekOfYear;
        for (Report report : reportsByTechnician) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(report.getInitialDate());
            numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            if (Objects.equals(weekNumber, numberWeekOfYear)) {
                reportsByTechnicianAndWeekNumber.add(report);
            }
        }
        return reportsByTechnicianAndWeekNumber;
    }

    private List<Report> getTechnicianById(List<Report> listReports,
            String idTechnician) {
        List<Report> reportsByTechnician = new ArrayList<>();
        listReports.stream().filter((report)
                -> (report.getIdTechnician().equals(idTechnician)))
                .forEachOrdered((report) -> {
                    reportsByTechnician.add(report);
                });
        return reportsByTechnician;
    }
}
