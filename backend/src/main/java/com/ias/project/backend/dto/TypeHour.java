/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Sebastian
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeHour {

    private RegularHour regularHour;
    private NightHour nightHour;
    private SundayHour sundayHour;
    private ExtraRegularHour extraRegularHour;
    private ExtraNightHour extraNightHour;
    private ExtraSundayHour extraSundayHour;

}
