package com.genc.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.genc.project.entities.User;
import com.genc.project.repositories.UserRepository;

@SpringBootApplication
@ComponentScan("com.genc.project")
public class OnlineEducationPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineEducationPlatformApplication.class, args);
		System.out.println("hello World!!!");
	}

}
