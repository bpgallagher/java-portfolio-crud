package com.sugata.javacrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JavaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCrudApplication.class, args);
	}
}
