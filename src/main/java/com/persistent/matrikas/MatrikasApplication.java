package com.persistent.matrikas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@SpringBootApplication
@ComponentScan(basePackages = "com.persistent.matrikas")
public class MatrikasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatrikasApplication.class, args);
	}

}
