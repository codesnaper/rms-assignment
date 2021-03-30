package com.logistic.rms.service;

import com.logistic.rms.daoEntities.Rate;
import com.logistic.rms.model.ActivitySummaryData;
import com.logistic.rms.model.FileUploadModel;
import com.logistic.rms.model.PageActivity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IActivityService {

    List<FileUploadModel> fileUploadModels(MultipartFile[] files) throws Exception;

    PageActivity getActivities(int pageNo, int pageSize) throws Exception;

    ActivitySummaryData geActivitySummaryData(long id) throws Exception;
}
