package com.school.webapp;

import com.school.webapp.Repository.MyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = MyRepository.class)
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

}
