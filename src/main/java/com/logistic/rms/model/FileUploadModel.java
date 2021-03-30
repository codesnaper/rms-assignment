package com.logistic.rms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode
@ToString
public class FileUploadModel {
    private String fileName;
    private String status;
    private String fileType;
    private long size;
    private String errorMessage;
}
