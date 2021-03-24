package com.logistic.rms;

import com.logistic.rms.dao.IRoleDao;
import com.logistic.rms.dao.IUserDao;
import com.logistic.rms.dao.IUserRoleDao;
import com.logistic.rms.daoEntities.Role;
import com.logistic.rms.daoEntities.User;
import com.logistic.rms.daoEntities.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootApplication
public class RmsApplication {

	public static void main(String[] args) {
		log.info("****Stating the Application*****");
		ApplicationContext ctx = SpringApplication.run(RmsApplication.class, args);
	}

}
