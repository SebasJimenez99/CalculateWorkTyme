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
import java.util.Calendar;
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
        String initialDateFirst = "2021-07-29 14:00:00";
        String finalDateFirst = "2021-07-29 15:30:00";
        String initialDateSecond = "2021-07-27 08:10:00";
        String finalDateSecond = "2021-07-27 12:20:00";
        String initialDateThird = "2021-07-28 08:10:00";
        String finalDateThird = "2021-07-28 21:20:00";
        String initialDateFourth = "2021-07-01 08:10:00";
        String finalDateFourth = "2021-08-01 14:20:00";
        String initialDateFifth = "2021-07-26 08:10:00";
        String finalDateFifth = "2021-07-26 14:20:00";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(1,
                        "asdc12", "adf31", initialDateFirst,
                        finalDateFirst))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(2,
                        "aasd5", "adf31", initialDateSecond,
                        finalDateSecond))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(3,
                        "aasd25", "adf31", initialDateThird,
                        finalDateThird))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(4,
                        "ahvn75", "adf31", initialDateFourth,
                        finalDateFourth))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(5,
                        "acxha3", "adf31", initialDateFifth,
                        finalDateFifth))))
                .andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/calculate?idTechnical=adf31&weekNumber=31"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
    }
}
