package com.logistic.rms.dao;

import com.logistic.rms.daoEntities.User;
import com.logistic.rms.daoEntities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IUserRoleDao extends JpaRepository<UserRole,Integer> {

}
