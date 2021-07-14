/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.unit;

import com.ias.project.backend.model.Report;
import com.ias.project.backend.repository.ReportRepository;
import com.ias.project.backend.service.implement.ReportServiceImp;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class ReportUnitTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImp reportService;

    @Test
    public void testCreateReport() {
        Date initialDate = new Date();
        Date finalDate = new Date(); 
        Report newReport = new Report(1, "abcsd1", "dsag", initialDate,
                finalDate);
        when(reportRepository.save(newReport)).thenReturn(newReport);
        Report createReport = reportService.createReport(newReport);
        assertEquals(createReport, newReport);
    }
}
