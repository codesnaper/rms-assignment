package com.logistic.rms.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "datasource")
@Component
@Data
@ToString(includeFieldNames = true,exclude = "password")
public class DBModel {

    private String className;

    private String url;

    private String username;

    private String password;
}
