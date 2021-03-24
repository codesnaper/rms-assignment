package com.logistic.rms.service;

import com.logistic.rms.daoEntities.Rate;

public interface IRateService {

    public Rate addRate(Rate rate) throws Exception;

    public Rate getRate(long id) throws Exception;

    public Rate updateRate(long id, Rate rate) throws Exception;

    public boolean deleteRate(long id) throws Exception;

    public Rate fallbackRate(long id) throws Exception;
}
