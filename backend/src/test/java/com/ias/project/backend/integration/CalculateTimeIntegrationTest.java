/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ias.project.backend.model.Report;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Sebastian
 */
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CalculateTimeIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(
                this.webApplicationContext).build();
    }

    @Test
    public void calculateHoursGiveOk() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat simpleFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        Date initialDateFirst = simpleFormat.parse("2021-07-13 14:00:00");
        Date finalDateFirst = simpleFormat.parse("2021-07-14 15:30:00");
        Date initialDateSecond = simpleFormat
                .parse("2021-07-14 08:10:00");
        Date finalDateSecond = simpleFormat.parse("2021-07-14 12:20:00");
        Date initialDateThird = simpleFormat
                .parse("2021-07-18 08:10:00");
        Date finalDateThird = simpleFormat.parse("2021-07-18 21:20:00");
        Date initialDateFourth = simpleFormat
                .parse("2021-07-15 08:10:00");
        Date finalDateFourth = simpleFormat.parse("2021-07-15 14:20:00");
        Date initialDateFifth = simpleFormat
                .parse("2021-07-16 08:10:00");
        Date finalDateFifth = simpleFormat.parse("2021-07-16 14:20:00");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(1,
                        "asdc12", "adf31", initialDateFirst.toString(),
                        finalDateFirst.toString()))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(2,
                        "aasd5", "adf31", initialDateSecond.toString(),
                        finalDateSecond.toString()))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(3,
                        "aasd25", "adf31", initialDateThird.toString(),
                        finalDateThird.toString()))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(4,
                        "ahvn75", "adf31", initialDateFourth.toString(),
                        finalDateFourth.toString()))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/calculate?idTechnical=adf31&weekNumber=28"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
    }
}
