package com.ias.project.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "DTO class that stores the schedule information")
public class Hour {

    @ApiModelProperty(notes = "Type hour associated with the schedule")
    private String type;
    @ApiModelProperty(notes = "List of hours associated with the schedule")
    private List<Float> listOfHour;
}
