package com.logistic.rms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.logistic.rms.daoEntities.Rate;
import com.logistic.rms.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Rest Resource for rate
 */
@RestController
@RequestMapping("rate")
public class RateController {

    @Autowired
    IRateService rateService;

    /**
     * Getting rate wrt id
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "isAuthenticated()")
    public Rate getRateById(@PathVariable(name = "id", required = true) long id, HttpServletRequest request) throws Exception {
        return rateService.getRate(id);
    }

    /**
     * Saving rate
     *
     * @param rate
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Rate addRate(@RequestBody Rate rate) throws Exception {
        return rateService.addRate(rate);
    }

    /**
     * Updating Rate
     *
     * @param id
     * @param rate
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Rate updateRate(@PathVariable(name = "id", required = true) long id, @RequestBody Rate rate) throws Exception {
        return rateService.updateRate(id, rate);
    }

    /**
     * Deleting Rate
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectNode deleteRate(@PathVariable(name = "id", required = true) long id) throws Exception {
        if (rateService.deleteRate(id)) {
            ObjectNode objectNode = new ObjectMapper().createObjectNode();
            objectNode.put("message", "Rate deleted Successfully");
            objectNode.put("id", id);
            return objectNode;
        }
        throw new Exception("Failed to delete record");
    }


}
