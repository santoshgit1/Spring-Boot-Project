package com.reporting;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@Slf4j
@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class ReportingApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReportingApiApplication.class, args);
		log.info("Reporting application has been started");
	}

}
