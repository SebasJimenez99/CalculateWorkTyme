/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.unit;

import com.ias.project.backend.dto.ExtraNightHour;
import com.ias.project.backend.dto.ExtraRegularHour;
import com.ias.project.backend.dto.ExtraSundayHour;
import com.ias.project.backend.dto.NightHour;
import com.ias.project.backend.dto.RegularHour;
import com.ias.project.backend.dto.SundayHour;
import com.ias.project.backend.dto.TypeHour;
import com.ias.project.backend.model.Report;
import com.ias.project.backend.repository.ReportRepository;
import com.ias.project.backend.service.implement.CalculateTimeServiceImp;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Sebastian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculateTimeUnitTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private CalculateTimeServiceImp calculateTimeService;

    @Test
    public void testGetReportByTechnicianAndWeek() {
        List<Report> listReport = new ArrayList<>();
        List<Float> listOfHourReg = new ArrayList<>();
        listOfHourReg.add(18.17f);
        List<Float> listOfHourNoc = new ArrayList<>();
        listOfHourNoc.add(11.5f);
        List<Float> listOfHourDom = new ArrayList<>();
        listOfHourDom.add(13.17f);
        List<Float> listOfHourExtReg = new ArrayList<>();
        listOfHourExtReg.add(12.34f);
        List<Float> listOfHourExtNoc = new ArrayList<>();
        listOfHourExtNoc.add(0.0f);
        List<Float> listOfHourExtDom = new ArrayList<>();
        listOfHourExtDom.add(0.0f);
        RegularHour regularHour = new RegularHour();
        regularHour.setListOfHour(listOfHourReg);
        regularHour.setType("Horas Normales");
        NightHour nightHour = new NightHour();
        nightHour.setListOfHour(listOfHourNoc);
        nightHour.setType("Horas Nocturnas");
        SundayHour sundayHour = new SundayHour();
        sundayHour.setListOfHour(listOfHourDom);
        sundayHour.setType("Horas Dominicales");
        ExtraRegularHour extraRegularHour = new ExtraRegularHour();
        extraRegularHour.setListOfHour(listOfHourExtReg);
        extraRegularHour.setType("Horas Normales Extra");
        ExtraNightHour extraNightHour = new ExtraNightHour();
        extraNightHour.setListOfHour(listOfHourExtNoc);
        extraNightHour.setType(null);
        ExtraSundayHour extraSundayHour = new ExtraSundayHour();
        extraSundayHour.setListOfHour(listOfHourExtDom);
        extraSundayHour.setType(null);
        TypeHour newTypeHour = new TypeHour(regularHour, nightHour,
                sundayHour, extraRegularHour, extraNightHour, extraSundayHour);
        String initialDate = "Wed Jul 14 22:20:42 COT 2021";
        String finalDate = "Wed Jul 14 20:20:42 COT 2021"; 
        Report newReport = new Report(1, "adf31", "dsag", initialDate,
                finalDate);
        listReport.add(newReport);
        when(reportRepository.save(newReport)).thenReturn(newReport);
        when(reportRepository.findAll()).thenReturn(listReport);
        TypeHour createReport = calculateTimeService
                .hoursOfOperation("adf31", 28);
        assertNull(createReport.getRegularHour().getType());
    }
}
