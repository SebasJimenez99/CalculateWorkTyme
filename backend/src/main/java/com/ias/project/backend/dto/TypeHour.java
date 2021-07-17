/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "DTO class that stores the type hour information")
public class TypeHour {

    @ApiModelProperty(notes = "Schedule regular associated with the type hour")
    private RegularHour regularHour;
    @ApiModelProperty(notes = "Schedule night associated with the type hour")
    private NightHour nightHour;
    @ApiModelProperty(notes = "Schedule sunday associated with the type hour")
    private SundayHour sundayHour;
    @ApiModelProperty(notes = "Schedule extra regular associated with the type "
            + "hour")
    private ExtraRegularHour extraRegularHour;
    @ApiModelProperty(notes = "Schedule extra night associated with the type "
            + "hour")
    private ExtraNightHour extraNightHour;
    @ApiModelProperty(notes = "Schedule extra sunday associated with the type "
            + "hour")
    private ExtraSundayHour extraSundayHour;

}
