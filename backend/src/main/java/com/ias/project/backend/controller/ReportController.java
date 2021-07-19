/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.controller;

import com.ias.project.backend.dto.TypeHour;
import com.ias.project.backend.model.Report;
import com.ias.project.backend.service.ReportService;
import com.ias.project.backend.utils.HttpConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping(value = "/reports")
@Api(value = "Creation of reports information",
        description = "Implementation API to get information from a report")
@Slf4j
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "Create and save a new Report for a technician")
    @ApiResponses(value = {
        @ApiResponse(code = HttpConstants.HTTP_STATUS_OK,
                message = "New report has been created",
                response = TypeHour.class),
        @ApiResponse(code = HttpConstants.HTTP_STATUS_BAD_REQUEST,
                message = "The action could not be performed")
    })
    @PostMapping
    public ResponseEntity<Report> createReport(
            @RequestBody final Report report) {
        log.info("Connecting api with controller to create a new report");
        Report response = reportService.createReport(report);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
