package com.nokia.ticktacktoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class TickTackToeApplication {
	private static final Logger SERVICE_LOG = LogManager.getLogger("service_logs");

	public static void main(String[] args) {
		SpringApplication.run(TickTackToeApplication.class, args);
		SERVICE_LOG.info("Ticktacktoe Service Started....");
	}

}
