package ru.mail.dimaushenko.springbootmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:/db/column/columnNames.properties", "classpath:/db/request/request.properties"})
@ComponentScan(basePackages = {
    "ru.mail.dimaushenko.springbootmodule.controller",
    "ru.mail.dimaushenko.service",
    "ru.mail.dimaushenko.repository",
    "ru.mail.dimaushenko.springbootmodule.runner"
})
public class SpringBootModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootModuleApplication.class, args);
	}

}
