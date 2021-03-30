package com.logistic.rms.controller;

import com.logistic.rms.model.Activity;
import com.logistic.rms.model.ActivitySummaryData;
import com.logistic.rms.model.FileUploadModel;
import com.logistic.rms.model.PageActivity;
import com.logistic.rms.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Rest Resource for rate
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("activitytracker")
public class ActivityController {

    @Autowired
    IActivityService activityService;

    @PostMapping("/uploadFile")
    public List<FileUploadModel> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws Exception{
        return activityService.fileUploadModels(files);
    }

    @GetMapping(value = "/activities",produces = {MediaType.APPLICATION_JSON_VALUE})
    public PageActivity getActivities(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                      @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) throws Exception{
        return activityService.getActivities(pageNo,pageSize);
    }

    @GetMapping(value = "/summaryData/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ActivitySummaryData getActivitySummaryData(@PathVariable(value = "id",required = true) long id) throws Exception{
        return activityService.geActivitySummaryData(id);
    }




}