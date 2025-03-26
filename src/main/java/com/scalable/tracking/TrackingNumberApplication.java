package com.scalable.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TrackingNumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingNumberApplication.class, args);
	}

}
