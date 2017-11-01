package com.sugata.javacrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.sugata.javacrud")
@EnableJpaRepositories("com.sugata.javacrud.repositories")
@EntityScan("com.sugata.javacrud.models")
public class JavaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCrudApplication.class, args);
	}
}
