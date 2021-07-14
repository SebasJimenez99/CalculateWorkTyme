/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ias.project.backend.config.ReportConfig;
import com.ias.project.backend.model.Report;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
public class ReportIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(
                this.webApplicationContext).build();
    }

    @Test
    public void createNewReportGiveOk() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Date initialDate = new Date();
        Date finalDate = new Date();        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Report(1,
                        "asdc12", "asdf31", initialDate, finalDate))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idService").value("asdc12"))
                .andExpect(jsonPath("$.idTechnician").exists())
                .andReturn();
    }
}
