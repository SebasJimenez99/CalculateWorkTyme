/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.controller;

import com.ias.project.backend.model.Report;
import com.ias.project.backend.service.CalculateTimeService;
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
public class CalculateTimeController {

    @Autowired
    private CalculateTimeService calculateTimeService;

    @GetMapping
    public ResponseEntity<Float> createReport(
            @RequestParam final String idTechnical,
            @RequestParam final Integer weekNumber) {
        Float response = calculateTimeService
                .hoursOfOperation(idTechnical, weekNumber);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
