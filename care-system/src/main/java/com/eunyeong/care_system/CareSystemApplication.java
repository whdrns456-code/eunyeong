package com.eunyeong.care_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 2. 여기를 수정하세요!
public class CareSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareSystemApplication.class, args);
	}

}
