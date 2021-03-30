package com.logistic.rms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class Record {

    @JsonProperty("record_def")
    private String recordDefination;

    @JsonProperty("time")
    private Date time;

    @JsonProperty("distance")
    private int distance;

    @JsonProperty("power")
    private int power;

    @JsonProperty("cadence")
    private int cadence;

}