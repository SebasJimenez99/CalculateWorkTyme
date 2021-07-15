/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service.implement;

import com.ias.project.backend.dto.DateAndType;
import com.ias.project.backend.dto.TypeHour;
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
    public Float hoursOfOperation(String idTechnician, Integer weekNumber) {
        List<Report> listReports = reportRepository.findAll();
        List<Report> reportsByTechnician = getTechnicianById(listReports,
                idTechnician);
        List<Report> reportsByTechnicianAndWeekNumber
                = getTechnicianByWeekNumber(reportsByTechnician, weekNumber);
        List<Float> hoursOfOperation
                = getTotalHoursOfOperation(reportsByTechnicianAndWeekNumber);
        Float totalHoursOfOperation = 0f;
        totalHoursOfOperation = hoursOfOperation.stream().map((hour) -> hour)
                .reduce(totalHoursOfOperation, (accumulator, _item)
                        -> accumulator + _item);
        log.info("Entry into service function to know the total hours = "
                + totalHoursOfOperation);
        return totalHoursOfOperation;
    }

    private List<Float> getTotalHoursOfOperation(
            List<Report> reportsByTechnicianAndWeekNumber) {
        List<Float> totalHoursOfOperation = new ArrayList<>();
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
            DateAndType dateAndType = workingHours(initialDate, finalDate);
            if (dateAndType != null) {
                Long initialTime = dateAndType.getInitialDate().getTime();
                Long finalTime = dateAndType.getFinalDate().getTime();
                Long mili = finalTime - initialTime;
                Long min = mili / (1000 * 60);
                Float hour = min / 60f;
                hour = Math.round(hour * 100) / 100f;
                log.info("Entry into service function to know the mins = "
                        + hour);
                totalHoursOfOperation.add(hour);
            }
        });
        return totalHoursOfOperation;
    }

    private void hourByType(String type, Float hour) {
        TypeHour typeHour = new TypeHour();
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
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek(4);
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

    private DateAndType workingHours(Date initialDate, Date finalDate) {
        DateAndType dateAndType = new DateAndType();
        Calendar calendarInitial = Calendar.getInstance();
        calendarInitial.setTime(initialDate);
        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTime(finalDate);
        Integer initialHour = calendarInitial.get(Calendar.HOUR_OF_DAY);
        Integer finalHour = calendarFinal.get(Calendar.HOUR_OF_DAY);
        Integer dayWeekInitial = calendarInitial.get(Calendar.DAY_OF_WEEK);
        Integer dayWeekFinal = calendarFinal.get(Calendar.DAY_OF_WEEK);
        if (dayWeekInitial >= 1 && dayWeekInitial <= 6 && initialHour >= 7
                && initialHour <= 20 && dayWeekFinal >= 1
                && dayWeekFinal <= 6 && finalHour >= 7 && finalHour <= 20) {
            dateAndType.setInitialDate(initialDate);
            dateAndType.setFinalDate(finalDate);
            dateAndType.setType("Horas Normales");
        }
        return dateAndType;
    }
}
