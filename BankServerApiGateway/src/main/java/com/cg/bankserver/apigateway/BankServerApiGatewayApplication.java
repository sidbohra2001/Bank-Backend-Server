package com.cg.bankserver.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankServerApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServerApiGatewayApplication.class, args);
	}

}
