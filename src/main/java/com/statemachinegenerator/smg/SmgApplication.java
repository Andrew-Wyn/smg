package com.statemachinegenerator.smg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class SmgApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmgApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return (ctx) -> {
			log.info("SMG application started");
		};
	}
}
