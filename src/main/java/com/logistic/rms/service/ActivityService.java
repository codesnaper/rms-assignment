package com.logistic.rms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistic.rms.dao.IActivityDao;
import com.logistic.rms.dao.IRecordDao;
import com.logistic.rms.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.input.BOMInputStream;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for rate
 */
@Service
@Slf4j
public class ActivityService implements IActivityService {

    @Autowired
    IRecordDao recordRepository;

    @Autowired
    IActivityDao activityRepository;
    public List<FileUploadModel> fileUploadModels(MultipartFile[] files) throws Exception {
        List<FileUploadModel> fileUploadModels = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                persistRecords(processFile(file));
                FileUploadModel fileUploadModel = new FileUploadModel();
                fileUploadModel.setFileName(file.getName());
                fileUploadModel.setFileType(file.getContentType());
                fileUploadModel.setSize(file.getSize());
                fileUploadModel.setStatus("success");
                fileUploadModel.setErrorMessage("File Uploaded Successfully.");
                fileUploadModels.add(fileUploadModel);
            } catch (Exception e) {
                FileUploadModel fileUploadModel = new FileUploadModel();
                fileUploadModel.setFileName(file.getName());
                fileUploadModel.setFileType(file.getContentType());
                fileUploadModel.setSize(file.getSize());
                fileUploadModel.setStatus("failed");
                fileUploadModel.setErrorMessage(e.getLocalizedMessage());
                fileUploadModels.add(fileUploadModel);
            }
        }

        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    FileUploadModel fileUploadModel = new FileUploadModel();
                    fileUploadModel.setFileName(file.getName());
                    fileUploadModel.setFileType(file.getContentType());
                    fileUploadModel.setSize(file.getSize());
                    return fileUploadModel;
                })
                .collect(Collectors.toList());
    }

    public PageActivity getActivities(int pageNo, int pageSize) throws Exception{
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        Page<com.logistic.rms.daoEntities.Activity> activityPage = activityRepository.findAll(pageable);
        PageActivity pageActivity = new PageActivity();
        pageActivity.setActivities(activityPage.getContent().stream().map(obj->{
            Activity activity = new Activity();
            activity.setId(obj.getId());
            activity.setActivityDef(obj.getActivityDef());
            activity.setActivityType(obj.getActivityType());
            activity.setName(obj.getName());
            activity.setStartTime(obj.getStartTime());
            return activity;
        }).collect(Collectors.toList()));
        pageActivity.setMaxPage(activityPage.getTotalPages());
        pageActivity.setTotalElement(activityPage.getTotalElements());
        pageActivity.setCurrentPage(pageNo);
        return pageActivity;
    }

    public ActivitySummaryData geActivitySummaryData(long id) throws Exception{
        Optional<com.logistic.rms.daoEntities.Activity> optionalActivity = activityRepository.findById(id);
        if(optionalActivity.isPresent()){
            ActivitySummaryData activitySummaryData = new ActivitySummaryData();
            Activity activity = new Activity();
            activity.setName(optionalActivity.get().getName());
            activity.setStartTime(optionalActivity.get().getStartTime());
            activity.setActivityType(optionalActivity.get().getActivityType());
            activity.setActivityDef(optionalActivity.get().getActivityDef());
            activity.setId(id);
            activitySummaryData.setActivity(activity);
            activitySummaryData.setAvgPower(optionalActivity.get().getRecordList().
                    parallelStream().mapToDouble(com.logistic.rms.daoEntities.Record::getPower).
                    average().orElse(Double.NaN));
            activitySummaryData.setAvgCadence(optionalActivity.get().getRecordList().
                    parallelStream().mapToDouble(com.logistic.rms.daoEntities.Record::getCadence).
                    average().orElse(Double.NaN));
            activitySummaryData.setTotalDistance((long) optionalActivity.get().getRecordList().
                    parallelStream().mapToDouble(com.logistic.rms.daoEntities.Record::getDistance).
                    sum());
            return activitySummaryData;
        }
        else {
            throw new NoSuchElementException("Activity not found with corresponding id");
        }
    }

    private List<Object> processFile(MultipartFile file) throws Exception {
        List<Object> objectList = new ArrayList<>();
        BufferedReader fileReader = new BufferedReader(new
                InputStreamReader(new BOMInputStream(file.getInputStream()), "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        Map<String, Integer> headerMap = csvParser.getHeaderMap();
        String type = "header";
        for (CSVRecord csvRecord : csvRecords) {
            System.out.println(csvRecord.toString());
            boolean flag = true;
            if (headerMap.get("activty_def") != null && csvRecord.get(headerMap.get("activty_def")).equals("record_def")) {
                headerMap.clear();
                type = "record";
                for (int inc = 0; inc < csvRecord.size(); ++inc) {
                    headerMap.put(csvRecord.get(inc), inc);
                }
                flag = false;
            }
            if(flag){
                Map<String, String> record = new HashMap<>();
                for (String stringSet : headerMap.keySet()) {
                    if(!stringSet.isEmpty()){
                        record.put(stringSet, csvRecord.get(headerMap.get(stringSet)));
                    }
                }
                ObjectMapper objectMapper = new ObjectMapper();
                Object objRecord;
                if (type.equals("header")) {
                    objRecord = objectMapper.convertValue(record, Activity.class);
                } else {
                    objRecord = objectMapper.convertValue(record, Record.class);
                }
                objectList.add(objRecord);
            }
        }
        return objectList;
    }

    @Transactional
    private void persistRecords(List<Object> objectList) throws Exception{
        Optional<Object> optionalActivity =objectList.stream().filter(object -> object instanceof Activity).findFirst();
        if(optionalActivity.isPresent()){
            com.logistic.rms.daoEntities.Activity activityDao = new com.logistic.rms.daoEntities.Activity();
            activityDao = new com.logistic.rms.daoEntities.Activity();
            activityDao.setName(((Activity) optionalActivity.get()).getName());
            activityDao.setActivityDef(((Activity) optionalActivity.get()).getActivityDef());
            activityDao.setActivityType(((Activity) optionalActivity.get()).getActivityType());
            activityDao.setStartTime(((Activity) optionalActivity.get()).getStartTime());
            activityDao = activityRepository.save(activityDao);
            objectList = objectList.stream().filter(obj -> !(obj instanceof Activity)).collect(Collectors.toList());
            for(Object obj: objectList){
                com.logistic.rms.daoEntities.Record recordDao = new com.logistic.rms.daoEntities.Record();
                recordDao.setActivity(activityDao);
                recordDao.setCadence(((Record) obj).getCadence());
                recordDao.setDistance(((Record) obj).getDistance());
                recordDao.setRecordDef(((Record) obj).getRecordDefination());
                recordDao.setPower(((Record) obj).getPower());
                recordDao.setTime(((Record) obj).getTime());
                recordRepository.save(recordDao);
            }
        }
    }
}
