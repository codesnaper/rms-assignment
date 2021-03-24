package com.logistic.rms.dao;

import com.logistic.rms.daoEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IUserDao extends JpaRepository<User,Integer> {

    @Query("select user from User user where user.userName= :username")
    public User getUsername(@Param("username") String userName);
}
