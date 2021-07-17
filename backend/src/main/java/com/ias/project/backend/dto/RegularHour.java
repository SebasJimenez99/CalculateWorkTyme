/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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
@ApiModel(description = "DTO class that stores the schedule information")
public class RegularHour {

    @ApiModelProperty(notes = "Type hour associated with the schedule")
    private String type;
    @ApiModelProperty(notes = "List of hours associated with the schedule")
    private List<Float> listOfHour;

}
