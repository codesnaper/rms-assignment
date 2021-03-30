package com.logistic.rms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class Activity {

    @JsonProperty("id")
    private long id;

    @JsonProperty("activty_def")
    private String activityDef;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String activityType;

    @JsonProperty("start_time")
    private Date startTime;
}
