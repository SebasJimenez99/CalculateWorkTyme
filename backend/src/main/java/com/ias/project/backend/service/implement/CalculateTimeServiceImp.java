/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service.implement;

import com.ias.project.backend.dto.DateAndType;
import com.ias.project.backend.dto.ExtraNightHour;
import com.ias.project.backend.dto.ExtraRegularHour;
import com.ias.project.backend.dto.ExtraSundayHour;
import com.ias.project.backend.dto.NightHour;
import com.ias.project.backend.dto.RegularHour;
import com.ias.project.backend.dto.SundayHour;
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
    public TypeHour hoursOfOperation(String idTechnician, Integer weekNumber) {
        log.info("Entry into service function to calculate the number hours");
        List<Report> listReports = reportRepository.findAll();
        List<Report> reportsByTechnician = getTechnicianById(listReports,
                idTechnician);
        List<Report> reportsByTechnicianAndWeekNumber
                = getTechnicianByWeekNumber(reportsByTechnician, weekNumber);
        List<TypeHour> listTotalHoursOfOperation
                = getTotalHoursOfOperation(reportsByTechnicianAndWeekNumber);
        TypeHour typeHour = getAllTypeHour(listTotalHoursOfOperation);
        return typeHour;
    }

    private List<TypeHour> getTotalHoursOfOperation(
            List<Report> reportsByTechnicianAndWeekNumber) {
        log.info("Entry into service function to get the total hours of "
                + "operation");
        List<Report> listHoursOfWeek = new ArrayList<>();
        List<TypeHour> listHoursByType = new ArrayList<>();
        reportsByTechnicianAndWeekNumber.forEach((report) -> {
            listHoursOfWeek.add(report);
            Float totalHoursOfOperation
                    = getTotalHoursOfWeek(listHoursOfWeek);
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
            List<DateAndType> dateAndType = workingHours(initialDate, finalDate,
                    totalHoursOfOperation);
            dateAndType.forEach((date) -> {
                Long initialTime = date.getInitialDate().getTime();
                Long finalTime = date.getFinalDate().getTime();
                Long mili = finalTime - initialTime;
                Long min = mili / (1000 * 60);
                Float hour = min / 60f;
                hour = Math.round(hour * 100) / 100f;
                TypeHour typeHour = hourByType(date.getType(), hour);
                listHoursByType.add(typeHour);
            });
        });
        return listHoursByType;
    }

    private TypeHour getAllTypeHour(List<TypeHour> listTotalHoursOfOperation) {
        log.info("Entry into service function to get all the types hours");
        TypeHour typeHour = new TypeHour();
        RegularHour regularHour = new RegularHour();
        NightHour nightHour = new NightHour();
        SundayHour sundayHour = new SundayHour();
        ExtraRegularHour extraRegularHour = new ExtraRegularHour();
        ExtraNightHour extraNightHour = new ExtraNightHour();
        ExtraSundayHour extraSundayHour = new ExtraSundayHour();
        List<Float> listHoursOfRegularHour = new ArrayList<>();
        List<Float> listHoursOfNightHour = new ArrayList<>();
        List<Float> listHoursOfSundayHour = new ArrayList<>();
        List<Float> listHoursOfExtraRegularHour = new ArrayList<>();
        List<Float> listHoursOfExtraNightHour = new ArrayList<>();
        List<Float> listHoursOfExtraSundayHour = new ArrayList<>();
        listTotalHoursOfOperation.forEach((type) -> {
            if (type.getRegularHour() != null) {
                listHoursOfRegularHour.add(type.getRegularHour()
                        .getListOfHour().get(0));
                regularHour.setType(type.getRegularHour().getType());
            } else if (type.getNightHour() != null) {
                listHoursOfNightHour.add(type.getNightHour()
                        .getListOfHour().get(0));
                nightHour.setType(type.getNightHour().getType());
            } else if (type.getSundayHour() != null) {
                listHoursOfSundayHour.add(type.getSundayHour()
                        .getListOfHour().get(0));
                sundayHour.setType(type.getSundayHour().getType());
            } else if (type.getExtraRegularHour() != null) {
                listHoursOfExtraRegularHour.add(type.getExtraRegularHour()
                        .getListOfHour().get(0));
                extraRegularHour.setType(type.getExtraRegularHour().getType());
            } else if (type.getExtraNightHour() != null) {
                listHoursOfExtraNightHour.add(type.getExtraNightHour()
                        .getListOfHour().get(0));
                extraNightHour.setType(type.getExtraNightHour().getType());
            } else if (type.getExtraSundayHour() != null) {
                listHoursOfExtraSundayHour.add(type.getExtraSundayHour()
                        .getListOfHour().get(0));
                extraSundayHour.setType(type.getExtraSundayHour().getType());
            }
        });
        regularHour.setListOfHour(sumAllHours(listHoursOfRegularHour));
        nightHour.setListOfHour(sumAllHours(listHoursOfNightHour));
        sundayHour.setListOfHour(sumAllHours(listHoursOfSundayHour));
        extraRegularHour.setListOfHour(sumAllHours(
                listHoursOfExtraRegularHour));
        extraNightHour.setListOfHour(sumAllHours(listHoursOfExtraNightHour));
        extraSundayHour.setListOfHour(sumAllHours(listHoursOfExtraSundayHour));
        typeHour.setRegularHour(regularHour);
        typeHour.setNightHour(nightHour);
        typeHour.setSundayHour(sundayHour);
        typeHour.setExtraRegularHour(extraRegularHour);
        typeHour.setExtraNightHour(extraNightHour);
        typeHour.setExtraSundayHour(extraSundayHour);
        return typeHour;
    }

    private Float getTotalHoursOfWeek(
            List<Report> reportsByTechnicianAndWeekNumber) {
        log.info("Entry into service function to get the total hours of the "
                + "week");
        List<Float> listHoursOfOperation = new ArrayList<>();
        Float totalHoursOfOperation = 0f;
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
            Float hour = min / 60f;
            hour = Math.round(hour * 100) / 100f;
            listHoursOfOperation.add(hour);
        });
        totalHoursOfOperation = listHoursOfOperation.stream()
                .map((hour) -> hour)
                .reduce(totalHoursOfOperation, (accumulator, _item)
                        -> accumulator + _item);
        return totalHoursOfOperation;
    }

    private List<Float> sumAllHours(List<Float> listHoursOfType) {
        log.info("Entry into service function to sum all hours receive");
        List<Float> hoursAdded = new ArrayList<>();
        Float totalHoursOfOperation = 0f;
        totalHoursOfOperation = listHoursOfType.stream()
                .map((hour) -> hour)
                .reduce(totalHoursOfOperation, (accumulator, _item)
                        -> accumulator + _item);
        hoursAdded.add(totalHoursOfOperation);
        return hoursAdded;
    }

    private TypeHour hourByType(String type, Float hour) {
        log.info("Entry into service function to separate the hours by type");
        List<Float> listOfHour = new ArrayList<>();
        TypeHour typeHour = new TypeHour();
        RegularHour regularHour = new RegularHour();
        NightHour nightHour = new NightHour();
        SundayHour sundayHour = new SundayHour();
        ExtraRegularHour extraRegularHour = new ExtraRegularHour();
        ExtraNightHour extraNightHour = new ExtraNightHour();
        ExtraSundayHour extraSundayHour = new ExtraSundayHour();
        switch (type) {
            case "Horas Normales":
                listOfHour.add(hour);
                regularHour.setListOfHour(listOfHour);
                regularHour.setType(type);
                typeHour.setRegularHour(regularHour);
                break;
            case "Horas Nocturnas":
                listOfHour.add(hour);
                nightHour.setListOfHour(listOfHour);
                nightHour.setType(type);
                typeHour.setNightHour(nightHour);
                break;
            case "Horas Dominicales":
                listOfHour.add(hour);
                sundayHour.setListOfHour(listOfHour);
                sundayHour.setType(type);
                typeHour.setSundayHour(sundayHour);
                break;
            case "Horas Normales Extra":
                listOfHour.add(hour);
                extraRegularHour.setListOfHour(listOfHour);
                extraRegularHour.setType(type);
                typeHour.setExtraRegularHour(extraRegularHour);
                break;
            case "Horas Nocturnas Extra":
                listOfHour.add(hour);
                extraNightHour.setListOfHour(listOfHour);
                extraNightHour.setType(type);
                typeHour.setExtraNightHour(extraNightHour);
                break;
            case "Horas Dominicales Extra":
                listOfHour.add(hour);
                extraSundayHour.setListOfHour(listOfHour);
                extraSundayHour.setType(type);
                typeHour.setExtraSundayHour(extraSundayHour);
                break;
            default:
                break;
        }
        return typeHour;
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

    private List<DateAndType> workingHours(Date initialDate, Date finalDate,
            Float totalHoursOfOperation) {
        log.info("Entry into service function to get working hours");
        List<DateAndType> listDateAndType = new ArrayList<>();
        Calendar calendarInitial = Calendar.getInstance();
        calendarInitial.setTime(initialDate);
        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTime(finalDate);
        Integer initialHour = calendarInitial.get(Calendar.HOUR_OF_DAY);
        Integer finalHour = calendarFinal.get(Calendar.HOUR_OF_DAY);
        Integer dayWeekInitial = calendarInitial.get(Calendar.DAY_OF_WEEK);
        Integer dayWeekFinal = calendarFinal.get(Calendar.DAY_OF_WEEK);
        List<DateAndType> listRegularDateAndType
                = regularHours(initialHour, finalHour, dayWeekInitial,
                        dayWeekFinal, initialDate, finalDate,
                        totalHoursOfOperation);
        List<DateAndType> listMultipleDateAndType
                = multipleHours(initialHour, finalHour, dayWeekInitial,
                        dayWeekFinal, initialDate, finalDate,
                        totalHoursOfOperation);
        List<DateAndType> listDominicalDateAndType
                = dominicalHours(dayWeekInitial,dayWeekFinal, initialDate,
                        finalDate, totalHoursOfOperation);
        List<DateAndType> listExtraRegularDateAndType
                = extraRegularHours(initialHour, finalHour, dayWeekInitial,
                        dayWeekFinal, initialDate, finalDate,
                        totalHoursOfOperation);
        List<DateAndType> listExtraMultipleDateAndType
                = extraMultipleHours(initialHour, finalHour, dayWeekInitial,
                        dayWeekFinal, initialDate, finalDate,
                        totalHoursOfOperation);
        List<DateAndType> listExtraDominicalDateAndType
                = extraDominicalHours(dayWeekInitial, dayWeekFinal, initialDate,
                        finalDate, totalHoursOfOperation);
        if (!listRegularDateAndType.isEmpty()) {
            listDateAndType = listRegularDateAndType;
        } else if (!listMultipleDateAndType.isEmpty()) {
            listDateAndType = listMultipleDateAndType;
        } else if (!listDominicalDateAndType.isEmpty()) {
            listDateAndType = listDominicalDateAndType;
        } else if (!listExtraRegularDateAndType.isEmpty()) {
            listDateAndType = listExtraRegularDateAndType;
        } else if (!listExtraMultipleDateAndType.isEmpty()) {
            listDateAndType = listExtraMultipleDateAndType;
        } else if (!listExtraDominicalDateAndType.isEmpty()) {
            listDateAndType = listExtraDominicalDateAndType;
        }
        return listDateAndType;
    }

    private List<DateAndType> regularHours(Integer initialHour,
            Integer finalHour, Integer dayWeekInitial, Integer dayWeekFinal,
            Date initialDate, Date finalDate, Float totalHoursOfOperation) {
        log.info("Entry into service function to get regular hours");
        DateAndType dateAndType = new DateAndType();
        List<DateAndType> listDateAndType = new ArrayList<>();
        if (dayWeekInitial != 1 && initialHour >= 7 && initialHour <= 20
                && dayWeekFinal != 1 && finalHour >= 7 && finalHour <= 20
                && totalHoursOfOperation <= 48f
                && Objects.equals(dayWeekInitial, dayWeekFinal)) {
            dateAndType.setInitialDate(initialDate);
            dateAndType.setFinalDate(finalDate);
            dateAndType.setType("Horas Normales");
            listDateAndType.add(dateAndType);
        }
        return listDateAndType;
    }

    private List<DateAndType> multipleHours(Integer initialHour,
            Integer finalHour, Integer dayWeekInitial, Integer dayWeekFinal,
            Date initialDate, Date finalDate, Float totalHoursOfOperation) {
        log.info("Entry into service function to get multiple types hours");
        DateAndType dateAndType = new DateAndType();
        DateAndType dateAndTypeTwo = new DateAndType();
        DateAndType dateAndTypeThree = new DateAndType();
        List<DateAndType> listDateAndType = new ArrayList<>();
        if (dayWeekInitial < dayWeekFinal && dayWeekInitial != 1
                && totalHoursOfOperation <= 48) {
            Integer diferencia = dayWeekFinal - dayWeekInitial;
            finalHour += 24 * diferencia;
            if (initialHour > 20 && dayWeekFinal != 1
                    && finalHour > 20 && finalHour < 31) {
                dateAndType.setInitialDate(initialDate);
                dateAndType.setFinalDate(finalDate);
                dateAndType.setType("Horas Nocturnas");
                listDateAndType.add(dateAndType);
            } else if (initialHour < 20 && dayWeekFinal != 1
                    && finalHour > 20 && finalHour <= 31) {
                dateAndType.setInitialDate(initialDate);
                Date dateTransform = new Date();
                dateTransform.setTime(initialDate.getTime());
                dateTransform.setHours(20);
                dateAndType.setFinalDate(dateTransform);
                dateAndType.setType("Horas Normales");
                listDateAndType.add(dateAndType);
                dateAndTypeTwo.setInitialDate(dateTransform);
                dateAndTypeTwo.setFinalDate(finalDate);
                dateAndTypeTwo.setType("Horas Nocturnas");
                listDateAndType.add(dateAndTypeTwo);
            } else if (initialHour < 20 && dayWeekFinal != 1
                    && finalHour > 31) {
                dateAndType.setInitialDate(initialDate);
                Date dateTransform = new Date();
                dateTransform.setTime(initialDate.getTime());
                dateTransform.setHours(20);
                dateAndType.setFinalDate(dateTransform);
                dateAndType.setType("Horas Normales");
                listDateAndType.add(dateAndType);
                Date dateTransformTwo = new Date();
                dateTransformTwo.setTime(finalDate.getTime());
                dateTransformTwo.setHours(7);
                dateAndTypeTwo.setInitialDate(dateTransform);
                dateAndTypeTwo.setFinalDate(dateTransformTwo);
                dateAndTypeTwo.setType("Horas Nocturnas");
                listDateAndType.add(dateAndTypeTwo);
                dateAndTypeThree.setInitialDate(dateTransformTwo);
                dateAndTypeThree.setFinalDate(finalDate);
                dateAndTypeThree.setType("Horas Normales");
                listDateAndType.add(dateAndTypeThree);
            }
        }
        return listDateAndType;
    }

    private List<DateAndType> dominicalHours(Integer dayWeekInitial,
            Integer dayWeekFinal, Date initialDate, Date finalDate,
            Float totalHoursOfOperation) {
        log.info("Entry into service function to get dominical hours");
        DateAndType dateAndType = new DateAndType();
        List<DateAndType> listDateAndType = new ArrayList<>();
        if (dayWeekInitial == 1 && dayWeekFinal == 1
                && totalHoursOfOperation <= 48) {
            dateAndType.setInitialDate(initialDate);
            dateAndType.setFinalDate(finalDate);
            dateAndType.setType("Horas Dominicales");
            listDateAndType.add(dateAndType);
        }
        return listDateAndType;
    }

    private List<DateAndType> extraRegularHours(Integer initialHour,
            Integer finalHour, Integer dayWeekInitial, Integer dayWeekFinal,
            Date initialDate, Date finalDate, Float totalHoursOfOperation) {
        log.info("Entry into service function to get extra regular hours");
        DateAndType dateAndTypeExtra = new DateAndType();
        List<DateAndType> listDateAndType = new ArrayList<>();
        if (dayWeekInitial != 1 && initialHour >= 7 && initialHour <= 20
                && dayWeekFinal != 1 && finalHour >= 7 && finalHour <= 20
                && totalHoursOfOperation > 48f
                && Objects.equals(dayWeekInitial, dayWeekFinal)) {
            dateAndTypeExtra.setInitialDate(initialDate);
            dateAndTypeExtra.setFinalDate(finalDate);
            dateAndTypeExtra.setType("Horas Normales Extra");
            listDateAndType.add(dateAndTypeExtra);
        }
        return listDateAndType;
    }

    private List<DateAndType> extraMultipleHours(Integer initialHour,
            Integer finalHour, Integer dayWeekInitial, Integer dayWeekFinal,
            Date initialDate, Date finalDate, Float totalHoursOfOperation) {
        log.info("Entry into service function to get extra multiple types"
                + " hours");
        DateAndType dateAndType = new DateAndType();
        DateAndType dateAndTypeTwo = new DateAndType();
        DateAndType dateAndTypeThree = new DateAndType();
        List<DateAndType> listDateAndType = new ArrayList<>();
        if (dayWeekInitial < dayWeekFinal && dayWeekInitial != 1
                && totalHoursOfOperation > 48f) {
            Integer diferencia = dayWeekFinal - dayWeekInitial;
            finalHour += 24 * diferencia;
            if (initialHour > 20 && dayWeekFinal != 1
                    && finalHour > 20 && finalHour < 31) {
                dateAndType.setInitialDate(initialDate);
                dateAndType.setFinalDate(finalDate);
                dateAndType.setType("Horas Nocturnas Extra");
                listDateAndType.add(dateAndType);
            } else if (initialHour < 20 && dayWeekFinal != 1
                    && finalHour > 20 && finalHour <= 31) {
                dateAndType.setInitialDate(initialDate);
                Date dateTransform = new Date();
                dateTransform.setTime(initialDate.getTime());
                dateTransform.setHours(20);
                dateAndType.setFinalDate(dateTransform);
                dateAndType.setType("Horas Normales Extra");
                listDateAndType.add(dateAndType);
                dateAndTypeTwo.setInitialDate(dateTransform);
                dateAndTypeTwo.setFinalDate(finalDate);
                dateAndTypeTwo.setType("Horas Nocturnas Extra");
                listDateAndType.add(dateAndTypeTwo);
            } else if (initialHour < 20 && dayWeekFinal != 1
                    && finalHour > 31) {
                dateAndType.setInitialDate(initialDate);
                Date dateTransform = new Date();
                dateTransform.setTime(initialDate.getTime());
                dateTransform.setHours(20);
                dateAndType.setFinalDate(dateTransform);
                dateAndType.setType("Horas Normales Extra");
                listDateAndType.add(dateAndType);
                Date dateTransformTwo = new Date();
                dateTransformTwo.setTime(finalDate.getTime());
                dateTransformTwo.setHours(7);
                dateAndTypeTwo.setInitialDate(dateTransform);
                dateAndTypeTwo.setFinalDate(dateTransformTwo);
                dateAndTypeTwo.setType("Horas Nocturnas Extra");
                listDateAndType.add(dateAndTypeTwo);
                dateAndTypeThree.setInitialDate(dateTransformTwo);
                dateAndTypeThree.setFinalDate(finalDate);
                dateAndTypeThree.setType("Horas Normales Extra");
                listDateAndType.add(dateAndTypeThree);
            }
        }
        return listDateAndType;
    }

    private List<DateAndType> extraDominicalHours(Integer dayWeekInitial,
            Integer dayWeekFinal, Date initialDate, Date finalDate,
            Float totalHoursOfOperation) {
        log.info("Entry into service function to get extra dominical hours");
        DateAndType dateAndType = new DateAndType();
        List<DateAndType> listDateAndType = new ArrayList<>();
        if (dayWeekInitial == 1 && dayWeekFinal == 1
                && totalHoursOfOperation > 48) {
            dateAndType.setInitialDate(initialDate);
            dateAndType.setFinalDate(finalDate);
            dateAndType.setType("Horas Dominicales Extra");
            listDateAndType.add(dateAndType);
        }
        return listDateAndType;
    }
}
