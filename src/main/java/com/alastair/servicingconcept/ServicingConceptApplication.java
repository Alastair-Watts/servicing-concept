package com.alastair.servicingconcept;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ServicingConceptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicingConceptApplication.class, args);
	}
}
