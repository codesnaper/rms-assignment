package com.logistic.rms.config;

import com.logistic.rms.model.DBModel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * App configuration Class.
 */
@Configuration
@Slf4j
public class AppConfig {

    private HikariConfig hikariConfig = new HikariConfig();

    @Autowired
    private DBModel dbModel;

    /**
     * Create dataSource.
     *
     * @return DataSource
     */
    @Bean
    public DataSource getDataSource() {
        log.debug("Setting up the datasource");
        hikariConfig.setDriverClassName(dbModel.getClassName());
        hikariConfig.setJdbcUrl(dbModel.getUrl());
        hikariConfig.setUsername(dbModel.getUsername());
        hikariConfig.setPassword(dbModel.getPassword());
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(hikariConfig);
    }

    /**
     * create the localSession factory object
     *
     * @return LocalSessionFactoryBean
     */
    private LocalSessionFactoryBean getLocalSessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getDataSource());
        localSessionFactoryBean.setPackagesToScan("com.logistic.rms");
        log.debug("Setting up the Session factory object");
        return localSessionFactoryBean;
    }

    /**
     * Setting the transaction Manager
     *
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getLocalSessionFactoryBean().getObject());
        log.debug("Setting up the transactionManager");
        return transactionManager;
    }

}
