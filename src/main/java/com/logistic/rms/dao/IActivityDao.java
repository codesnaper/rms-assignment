package com.logistic.rms.dao;

import com.logistic.rms.daoEntities.Activity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityDao extends PagingAndSortingRepository<Activity,Long> {
}
