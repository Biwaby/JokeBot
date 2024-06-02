package com.biwaby.projects.jokebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class JokeBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(JokeBotApplication.class, args);
	}
}
