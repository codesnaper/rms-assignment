package com.logistic.rms.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SurchargeModel implements Serializable {

    private  int surchargeRate;

    private String surchargeDescription;
}
