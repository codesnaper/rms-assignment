package com.logistic.rms.dao;

import com.logistic.rms.daoEntities.Activity;
import com.logistic.rms.daoEntities.Record;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecordDao extends PagingAndSortingRepository<Record,Long> {
}
