package com.logistic.rms.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SurchargeModelTest {
    private SurchargeModel surchargeModel= new SurchargeModel();

    @Test
    void setSurchargeRate() {
        surchargeModel.setSurchargeRate(100);
        Assert.assertNotNull(surchargeModel.getSurchargeRate());
    }

    @Test
    void setSurchargeDescription() {
        surchargeModel.setSurchargeDescription("XYZ");
        Assert.assertNotNull(surchargeModel.getSurchargeDescription());
    }

    @Test
    void testEquals() {
        Assert.assertTrue(surchargeModel.equals(surchargeModel));
    }

    @Test
    void testHashCode() {
        Assert.assertNotNull(surchargeModel.hashCode());
    }

    @Test
    void testToString() {
        Assert.assertNotNull(surchargeModel.toString());
    }
}