package com.logistic.rms.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.rms.Exception.IDNotFoundExcpetion;
import com.logistic.rms.dao.IRateDAO;
import com.logistic.rms.daoEntities.Rate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.ServiceNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Service class for rate
 */
@Service
@Slf4j
public class RateService implements IRateService {

    @Autowired
    private IRateDAO rateDAO;

    @Value("${rms.surcharge.url}")
    private String surchargeUrl;

    /**
     *
     * @param rate
     * @return
     */
    @Override
    @Transactional
    public Rate addRate(Rate rate) throws Exception{
        log.info("Adding Rate ");
        try {
            Rate rate1 =  rateDAO.saveAndFlush(rate);
            log.debug("Rate has been saved , Rate = {}",rate1.toString());
            return rate1;
        }
        catch (DataIntegrityViolationException exception){
            log.error("Value is missing. Rate_Expiration_Date and Rate_Effective_Date is mandatory");
            throw new IllegalArgumentException("Amount, Rate_Expiration_Date and Rate_Effective_Date is mandatory");
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Transactional
    public Rate fallbackRate(long id) throws Exception{
        log.debug("Fetching Rate value from fallback");
        Optional<com.logistic.rms.daoEntities.Rate> rate =  rateDAO.findById(id);
        if(!rate.isPresent()){
            log.error("Rate not found with corresponding id");
            throw new IDNotFoundExcpetion("RateId not found in RMS");
        }
        else {
            return rate.get();
        }
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackRate")
    @Transactional
    public Rate getRate(long id) throws Exception{
        log.info("Fetching the rate for {}",id);
        Optional<com.logistic.rms.daoEntities.Rate> rate =  rateDAO.findById(id);
        if(!rate.isPresent()){
            log.error("Rate not found with corresponding id");
            throw new IDNotFoundExcpetion("RateId not found in RMS");
        }
        else {
            Rate rate1 = rate.get();
            log.debug("Fetching the surcharge from external Url = '{}'",surchargeUrl);
            ResponseEntity<String> stringResponseEntity = new RestTemplate().getForEntity(surchargeUrl, String.class);
            if(stringResponseEntity.getStatusCode().equals(HttpStatus.OK)){
                log.debug("Successfully retrived the surcharge data");
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(stringResponseEntity.getBody());
                rate1.setSurchargeRate(root.path("surchargeRate").asInt());
                rate1.setSurchargeDescription(root.path("surchargeDescription").asText());
                log.debug("Concatinating data in rate . Rate={}",rate1.toString());
                return rate1;
            }
            else{
                log.error("Failed in fetching data from service call with status {} and message as {}",stringResponseEntity.getStatusCode(),stringResponseEntity.getBody());
                throw new ServiceNotFoundException(String.format("Surcharge Service return with status %s and error messgae %s",stringResponseEntity.getStatusCode(),stringResponseEntity.getBody()));
            }

        }
    }

    @Override
    @Transactional
    public Rate updateRate(long id, Rate rate) throws Exception {
        log.info("Updating Rate Data with corresponding id {} and with data = Rate = {}",id,rate.toString());
        if(!rateDAO.findById(id).isPresent()){
            log.error("Failed to find the rate id");
            throw new IDNotFoundExcpetion("RateId not found in RMS");
        }
        else {
            rate.setId(id);
            log.debug("Updated Data Successfully");
            return rateDAO.save(rate);
        }
    }

    @Override
    @Transactional
    public boolean deleteRate(long id) throws Exception {
        log.info("Deleting the rate wrt id {}",id);
        if(!rateDAO.findById(id).isPresent()){
            log.error("Failed to delete the rate as id not found");
            throw new IDNotFoundExcpetion("RateId not found in RMS");
        }
        rateDAO.deleteById(id);
        log.debug("Rate deleted successfully");
        return true;
    }
}
