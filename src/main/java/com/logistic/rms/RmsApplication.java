package com.logistic.rms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@SpringBootApplication
public class RmsApplication {

	public static void main(String[] args) {
		log.info("****Stating the Application*****");
		ApplicationContext ctx = SpringApplication.run(RmsApplication.class, args);
	}

}
