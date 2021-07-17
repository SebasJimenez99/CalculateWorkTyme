/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.controller;

import com.ias.project.backend.dto.TypeHour;
import com.ias.project.backend.service.CalculateTimeService;
import com.ias.project.backend.utils.HttpConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping(value = "/calculate")
@Api(value = "Calculation of hours worked by technician",
        description = "Implementation API to get calculation from a report")
@Slf4j
public class CalculateTimeController {

    @Autowired
    private CalculateTimeService calculateTimeService;

    @ApiOperation(value = "Get information from a report to calculate time "
            + "worked")
    @ApiResponses(value = {
        @ApiResponse(code = HttpConstants.HTTP_STATUS_OK,
                message = "Calculation has been successful",
                response = TypeHour.class),
        @ApiResponse(code = HttpConstants.HTTP_STATUS_BAD_REQUEST,
                message = "The action could not be performed")
    })
    @GetMapping
    public ResponseEntity<TypeHour> calculateTime(
            @RequestParam final String idTechnical,
            @RequestParam final Integer weekNumber) {
        log.info("Connecting api with controller to calculation a report by "
                + "technician id an week number of year");
        TypeHour response = calculateTimeService
                .hoursOfOperation(idTechnical, weekNumber);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
