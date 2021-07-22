/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.service;

import com.ias.project.backend.dto.TypeHour;

/**
 *
 * @author Sebastian
 */
public interface CalculateTimeService {

    TypeHour hoursOfOperation(String idTechnician, Integer weekNumber);
}
