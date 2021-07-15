/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service.implement;

import com.ias.project.backend.model.Report;
import com.ias.project.backend.repository.ReportRepository;
import com.ias.project.backend.service.CalculateTimeService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastian
 */
@Service
@Slf4j
public class CalculateTimeServiceImp implements CalculateTimeService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Long hoursOfOperation(String idTechnician, Integer weekNumber) {
        List<Report> listReports = reportRepository.findAll();
        log.info("Entry into service function to know listReport Initial= "
                + listReports.get(0).getInitialDate());
        List<Report> reportsByTechnician = getTechnicianById(listReports,
                idTechnician);
        List<Report> reportsByTechnicianAndWeekNumber
                = getTechnicianByWeekNumber(reportsByTechnician, weekNumber);
        List<Long> hoursOfOperation
                = getTotalHoursOfOperation(reportsByTechnicianAndWeekNumber);
        Long totalHoursOfOperation = 0L;
        totalHoursOfOperation = hoursOfOperation.stream().map((hours) -> hours)
                .reduce(totalHoursOfOperation, (accumulator, _item)
                        -> accumulator + _item);
        log.info("Entry into service function to know the total hours = "
                + totalHoursOfOperation);
        return totalHoursOfOperation;
    }

    private List<Long> getTotalHoursOfOperation(
            List<Report> reportsByTechnicianAndWeekNumber) {
        List<Long> totalHoursOfOperation = new ArrayList<>();
        reportsByTechnicianAndWeekNumber.forEach((report) -> {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date initialDate = new Date();
            Date finalDate = new Date();
            try {
                initialDate = dateFormat.parse(report.getInitialDate());
                finalDate = dateFormat.parse(report.getFinalDate());
            } catch (ParseException ex) {
                Logger.getLogger(CalculateTimeServiceImp.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            Long initialTime = initialDate.getTime();
            Long finalTime = finalDate.getTime();
            Long mili = finalTime - initialTime;
            Long min = mili / (1000 * 60);
            Long hour = min / 60;
            log.info("Entry into service function to know the mins = "
                + min);
            totalHoursOfOperation.add(hour);
        });
        return totalHoursOfOperation;
    }

    private List<Report> getTechnicianByWeekNumber(
            List<Report> reportsByTechnician, Integer weekNumber) {
        List<Report> reportsByTechnicianAndWeekNumber = new ArrayList<>();
        Integer numberWeekOfYear = 0;
        for (Report report : reportsByTechnician) {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date initialDate = new Date();
            try {
                initialDate = dateFormat.parse(report.getInitialDate());
            } catch (ParseException ex) {
                Logger.getLogger(CalculateTimeServiceImp.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(initialDate);
            numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            if (Objects.equals(weekNumber, numberWeekOfYear)) {
                reportsByTechnicianAndWeekNumber.add(report);
            }
        }
        log.info("Entry into service function to know the week of year = "
                + numberWeekOfYear);
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
