package com.logistic.rms.Exception;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ErrorTest {

    private Error err = new Error();

    @Test
    void setStatus() {
        err.setStatus(HttpStatus.OK);
        Assert.assertEquals(HttpStatus.OK,err.getStatus());
    }

    @Test
    void setPath() {
        err.setPath("/path");
        Assert.assertEquals("/path",err.getPath());

    }

    @Test
    void setTimestamp() {
        err.setTimestamp(new Date());
        Assert.assertNotNull(err.getTimestamp());
    }

    @Test
    void setMessage() {
        err.setMessage("path");
        Assert.assertNotNull(err.getMessage());
    }

    @Test
    void testEquals() {
        Assert.assertTrue(err.equals(err));
    }

    @Test
    void testHashCode() {
        Assert.assertNotNull(err.hashCode());
    }

    @Test
    void testToString() {
        Assert.assertNotNull(err.toString());
    }
}