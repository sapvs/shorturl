package com.test.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TinyURLApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyURLApplication.class, args);
	}

}
