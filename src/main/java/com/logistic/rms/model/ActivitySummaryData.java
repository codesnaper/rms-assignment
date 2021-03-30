package com.logistic.rms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class ActivitySummaryData {

    @JsonProperty("activity")
    private Activity activity;

    @JsonProperty("total_distance")
    private long totalDistance;

    @JsonProperty("average_power")
    private double avgPower;

    @JsonProperty("average_cadence")
    private double avgCadence;
}
