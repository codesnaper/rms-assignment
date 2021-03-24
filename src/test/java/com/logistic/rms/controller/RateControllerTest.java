package com.logistic.rms.controller;

import com.logistic.rms.JSONUtil;
import com.logistic.rms.RmsApplication;
import com.logistic.rms.config.HealthActuator;
import com.logistic.rms.daoEntities.Rate;
import com.logistic.rms.service.IRateService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = RmsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")

class RateControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    IRateService rateService;

    @Test
    public void testGetRateById() throws Exception{
        Rate rate = new Rate();
        rate.setId(1);
        BDDMockito.given(rateService.getRate(Mockito.anyLong())).willReturn(rate);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/rate/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Basic dXNlcjpwYXNzd29yZA==")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateid", CoreMatchers.is(1)));
        Mockito.verify(rateService, VerificationModeFactory.times(1)).getRate(Mockito.anyLong());
        Mockito.reset(rateService);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/rate/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testAddRate() throws Exception{
        Rate rate = new Rate();
        rate.setId(1);
        rate.setExpirationDate(new Date());
        rate.setEffectiveDate(new Date());
        BDDMockito.given(rateService.addRate(Mockito.any())).willReturn(rate);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rate/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(rate))
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQ=")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateid", CoreMatchers.is(1)));
        Mockito.verify(rateService, VerificationModeFactory.times(1)).addRate(Mockito.any());
        Mockito.reset(rateService);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/rate/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(rate))
                        .header("Authorization","Basic dXNlcjpwYXNzd29yZA==")
        )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/rate/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(rate))
                        .header("Authorization","Basic dXNlcjpwYXNzd29SHU==")
        )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()); }

    @Test
    public void testUpdateRate() throws Exception{
        Rate rate = new Rate();
        rate.setId(1);
        rate.setExpirationDate(new Date());
        rate.setEffectiveDate(new Date());
        BDDMockito.given(rateService.updateRate(Mockito.anyLong(),Mockito.any())).willReturn(rate);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/rate/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(rate))
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQ=")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateid", CoreMatchers.is(1)));
        Mockito.verify(rateService, VerificationModeFactory.times(1)).updateRate(Mockito.anyLong(),Mockito.any());
        Mockito.reset(rateService);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/rate/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(rate))
                        .header("Authorization","Basic dXNlcjpwYXNzd29yZA==")
        )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteRate() throws Exception{
        BDDMockito.given(rateService.deleteRate(Mockito.anyLong())).willReturn(true);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/rate/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQ=")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        BDDMockito.given(rateService.deleteRate(Mockito.anyLong())).willReturn(false);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/rate/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Basic YWRtaW46cGFzc3dvcmQ=")
        )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        Mockito.verify(rateService, VerificationModeFactory.times(2)).deleteRate(Mockito.anyLong());
        Mockito.reset(rateService);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/rate/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Basic dXNlcjpwYXNzd29yZA==")
        )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public  void healthCheckUpTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator/health")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("UP")));
        HealthActuator healthActuator = new HealthActuator();
        Assert.assertNotNull(healthActuator.health());
    }

}