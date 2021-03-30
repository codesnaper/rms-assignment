package com.logistic.rms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class PageActivity {

    @JsonProperty("activities")
    private List<Activity> activities;

    @JsonProperty("maxPage")
    private int maxPage;

    @JsonProperty("currentPage")
    private int currentPage;

    @JsonProperty("totalElement")
    private long totalElement;
}
