package com.logistic.rms.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class Error {

    private HttpStatus status;

    private String path;

    private Date timestamp;

    private String message;
}
