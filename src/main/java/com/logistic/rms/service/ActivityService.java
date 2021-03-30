package com.logistic.rms.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.rms.Exception.IDNotFoundExcpetion;
import com.logistic.rms.dao.IRateDAO;
import com.logistic.rms.daoEntities.Rate;
import com.logistic.rms.model.FileUploadModel;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ServiceNotFoundException;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for rate
 */
@Service
@Slf4j
public class ActivityService implements IActivityService {

    public List<FileUploadModel> fileUploadModels(MultipartFile[] files) throws Exception{
        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return processFile(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private FileUploadModel processFile(MultipartFile file) throws Exception{
        BufferedReader fileReader = new BufferedReader(new
                InputStreamReader(file.getInputStream(), "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        for (CSVRecord csvRecord : csvRecords) {
            System.out.println(csvRecord);
        }
        FileUploadModel fileUploadModel = new FileUploadModel();
        fileUploadModel.setFileName(file.getName());
        fileUploadModel.setFileType(file.getContentType());
        fileUploadModel.setSize(file.getSize());
        return fileUploadModel;
    }
}
