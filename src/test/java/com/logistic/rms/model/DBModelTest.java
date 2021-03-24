package com.logistic.rms.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = DBModel.class)
@TestPropertySource("classpath:application-test.properties")
class DBModelTest {
    @Autowired
    private DBModel dbModel;

    @Test
    void setClassName() {
        Assert.assertEquals(dbModel.getClassName(),"org.h2.Driver");
    }

    @Test
    void setUrl() {
        Assert.assertEquals(dbModel.getUrl(),"jdbc:h2:mem:testdb");
    }

    @Test
    void setUsername() {
        Assert.assertEquals(dbModel.getUsername(),"username");
    }

    @Test
    void setPassword() {
        Assert.assertEquals(dbModel.getPassword(),"password");
    }

    @Test
    void testEquals() {
        Assert.assertTrue(dbModel.equals(dbModel));
    }

    @Test
    void testHashCode() {
        Assert.assertNotNull(dbModel.hashCode());
    }

    @Test
    void testToString() {
        Assert.assertNotNull(dbModel.toString());
    }
}