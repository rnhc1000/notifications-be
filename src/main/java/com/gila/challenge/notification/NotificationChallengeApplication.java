package com.gila.challenge.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationChallengeApplication implements CommandLineRunner {

	final Logger logger = LoggerFactory.getLogger(NotificationChallengeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NotificationChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("database, JPA UP and Running!!!");
	}
}
