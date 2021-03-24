package com.logistic.rms.daoEntities;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RateTest {

    private Rate rate = new Rate();
    @Test
    void getId() {
        rate.setId(1);
        Assert.assertEquals(rate.getId(),1);
    }

    @Test
    void getDescription() {
        rate.setDescription("Sample");
        Assert.assertEquals(rate.getDescription(),"Sample");
    }

    @Test
    void getExpirationDate() {
        rate.setExpirationDate(new Date());
        Assert.assertNotNull(rate.getExpirationDate());
    }

    @Test
    void getEffectiveDate() {
        rate.setEffectiveDate(new Date());
        Assert.assertNotNull(rate.getEffectiveDate());
    }

    @Test
    void getCreated() {
        rate.setCreated(new Date());
        Assert.assertNotNull(rate.getCreated());
    }

    @Test
    void getSurchargeRate() {
        rate.setSurchargeRate(100);
        Assert.assertNotNull(rate.getSurchargeRate());
    }

    @Test
    void getSurchargeDescription() {
        rate.setSurchargeDescription("Des");
        Assert.assertNotNull(rate.getSurchargeDescription());
    }

    @Test
    void getIs_deleted() {
        rate.setIs_deleted(true);
        Assert.assertTrue(rate.getIs_deleted());
    }

    @Test
    void testToString() {
        Assert.assertNotNull(rate.toString());
    }

    @Test
    void testEquals() {
        Assert.assertNotNull(rate.equals(rate));
    }

    @Test
    void testHashCode() {
        Assert.assertNotNull(rate.hashCode());
    }
}