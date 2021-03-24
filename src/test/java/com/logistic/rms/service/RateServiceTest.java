package com.logistic.rms.service;

import com.logistic.rms.Exception.IDNotFoundExcpetion;
import com.logistic.rms.RmsApplication;
import com.logistic.rms.dao.IRateDAO;
import com.logistic.rms.daoEntities.Rate;
import org.hibernate.PropertyValueException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = RmsApplication.class)
class RateServiceTest {

    @MockBean
    private IRateDAO rateDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Autowired
    IRateService rateService;

    @Test
    void addRate() throws Exception {
        Rate rate = new Rate();
        rate.setId(1);
        rate.setDescription("Sample");
        Mockito.when(rateDAO.saveAndFlush(Mockito.any())).thenReturn(rate);
        rate = rateService.addRate(new Rate());
        Mockito.verify(rateDAO, VerificationModeFactory.times(1)).saveAndFlush(Mockito.any());
        Mockito.reset(rateDAO);
    }

    @Test
    void addRateWithPropertyValueException() throws Exception {
        Rate rate = new Rate();
        rate.setId(1);
        rate.setDescription("Sample");
        Mockito.when(rateDAO.saveAndFlush(Mockito.any())).thenThrow(PropertyValueException.class);
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> rateService.addRate(new Rate())
        );
        Assert.assertEquals(thrown.getLocalizedMessage(),"Amount, Rate_Expiration_Date and Rate_Effective_Date is mandatory");
        Assert.assertTrue(thrown instanceof IllegalArgumentException);
        Mockito.verify(rateDAO, VerificationModeFactory.times(1)).saveAndFlush(Mockito.any());
        Mockito.reset(rateDAO);
    }

    @Test
    void addRateWithException() throws Exception {
        Rate rate = new Rate();
        rate.setId(1);
        rate.setDescription("Sample");
        Mockito.when(rateDAO.saveAndFlush(Mockito.any())).thenThrow(NullPointerException.class);
        NullPointerException thrown = Assertions.assertThrows(
                NullPointerException.class,
                () -> rateService.addRate(new Rate())
        );
        Assert.assertTrue(thrown instanceof NullPointerException);
        Mockito.verify(rateDAO, VerificationModeFactory.times(1)).saveAndFlush(Mockito.any());
        Mockito.reset(rateDAO);
    }

    @Test
    void getRatewithIDNotFoundException() {
        Optional<Rate> optionalRate = Optional.empty();
        Mockito.when(rateDAO.findById(Mockito.anyLong())).thenReturn(optionalRate);
        IDNotFoundExcpetion thrown = Assertions.assertThrows(
                IDNotFoundExcpetion.class,
                () -> rateService.getRate(1)
        );
        Assert.assertEquals(thrown.getLocalizedMessage(),"RateId not found in RMS");
        Assert.assertTrue(thrown instanceof IDNotFoundExcpetion);
        Mockito.reset(rateDAO);

    }

    @Test
    public void testGetRate() throws Exception{
        Rate rate = new Rate();
        Optional<Rate> optionalRate = Optional.of(rate);
        Mockito.when(rateDAO.findById(Mockito.anyLong())).thenReturn(optionalRate);
        rateService.getRate(1);
        Mockito.reset(rateDAO);

    }

    @Test
    public void testfallbackRate() throws Exception{
        Rate rate = new Rate();
        Optional<Rate> optionalRate = Optional.of(rate);
        Mockito.when(rateDAO.findById(Mockito.anyLong())).thenReturn(optionalRate);
        rateService.fallbackRate(1);
        Mockito.reset(rateDAO);

    }

    @Test
    void updateRatewithNotFound() {
        Optional<Rate> optionalRate = Optional.empty();
        Mockito.when(rateDAO.findById(Mockito.anyLong())).thenReturn(optionalRate);
        IDNotFoundExcpetion thrown = Assertions.assertThrows(
                IDNotFoundExcpetion.class,
                () -> rateService.updateRate(1,new Rate())
        );
        Assert.assertEquals(thrown.getLocalizedMessage(),"RateId not found in RMS");
        Assert.assertTrue(thrown instanceof IDNotFoundExcpetion);
        Mockito.verify(rateDAO, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(rateDAO);
    }

    @Test
    public void testupdateRate() throws Exception{
        Rate rate = new Rate();
        Optional<Rate> optionalRate = Optional.of(rate);
        Mockito.when(rateDAO.findById(Mockito.anyLong())).thenReturn(optionalRate);
        rateService.updateRate(1,new Rate());
        Mockito.verify(rateDAO, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(rateDAO);
    }

    @Test
    public void testdeleteRate() throws Exception{
        Rate rate = new Rate();
        Optional<Rate> optionalRate = Optional.of(rate);
        Mockito.when(rateDAO.findById(Mockito.anyLong())).thenReturn(optionalRate);
        rateService.deleteRate(1);
        Mockito.verify(rateDAO, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(rateDAO);
    }

    @Test
    void deleteRatewithNotFound() {
        Optional<Rate> optionalRate = Optional.empty();
        Mockito.when(rateDAO.findById(Mockito.anyLong())).thenReturn(optionalRate);
        IDNotFoundExcpetion thrown = Assertions.assertThrows(
                IDNotFoundExcpetion.class,
                () -> rateService.deleteRate(1)
        );
        Assert.assertEquals(thrown.getLocalizedMessage(),"RateId not found in RMS");
        Assert.assertTrue(thrown instanceof IDNotFoundExcpetion);
        Mockito.verify(rateDAO, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(rateDAO);
    }

    @Test
    void deleteRate() {
    }
}