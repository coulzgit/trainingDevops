package com.UnitTest.springUnitTest;

import com.UnitTest.springUnitTest.domains.ToDo;
import com.UnitTest.springUnitTest.domains.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringUnitTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringUnitTestApplication.class, args);
	}


	private static final Logger logger = LoggerFactory.getLogger(SpringUnitTestApplication.class);


	@Bean
	public CommandLineRunner setup(ToDoRepository toDoRepository) {
		return (args) -> {
			toDoRepository.save(new ToDo("Remove unused imports", true));
			toDoRepository.save(new ToDo("Clean the code", true));
			toDoRepository.save(new ToDo("Build the artifacts", false));
			toDoRepository.save(new ToDo("Deploy the jar file", true));
			logger.info("The sample data has been generated");
		};
	}

}
