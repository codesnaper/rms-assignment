package com.logistic.rms.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class IDNotFoundExcpetion extends Exception {
    public IDNotFoundExcpetion() {
        super();
    }
    public IDNotFoundExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
    public IDNotFoundExcpetion(String message) {
        super(message);
    }
    public IDNotFoundExcpetion(Throwable cause) {
        super(cause);
    }
}
