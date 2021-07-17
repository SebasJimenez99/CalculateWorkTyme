/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ias.project.backend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name="report_service")
@ApiModel(description = "Model class that stores the report information")
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Id associated with the report")
    private Integer id;
    @ApiModelProperty(notes = "Service id associated with the report")
    private String idService;
    @ApiModelProperty(notes = "Technician id associated with the report")
    private String idTechnician;
    @ApiModelProperty(notes = "Initial date associated with the report")
    private String initialDate;
    @ApiModelProperty(notes = "Final date associated with the report")
    private String finalDate;

}
