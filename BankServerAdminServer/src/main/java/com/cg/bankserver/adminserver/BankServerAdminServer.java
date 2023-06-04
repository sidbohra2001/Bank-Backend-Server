package com.cg.bankserver.adminserver;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class BankServerAdminServer {

	public static void main(String[] args) {
//		SpringApplication.run(BankServerAdminServer.class, args);
		new SpringApplicationBuilder(BankServerAdminServer.class)
				.web(WebApplicationType.REACTIVE)
				.run(args);
	}
}
