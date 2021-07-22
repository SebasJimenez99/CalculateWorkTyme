/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
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
@ApiModel(description = "DTO class that stores the date and type information")
public class DateAndType {

    @ApiModelProperty(notes = "Initial date associated with the date")
    private Date initialDate;
    @ApiModelProperty(notes = "Final date associated with the date")
    private Date finalDate;
    @ApiModelProperty(notes = "Type associated with the type report")
    private String type;

}
