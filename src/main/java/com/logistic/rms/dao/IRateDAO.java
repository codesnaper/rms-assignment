package com.logistic.rms.dao;

import com.logistic.rms.daoEntities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IRateDAO extends JpaRepository<Rate,Long> {

}
