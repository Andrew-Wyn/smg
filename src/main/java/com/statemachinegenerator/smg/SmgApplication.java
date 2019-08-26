package com.statemachinegenerator.smg;

import com.statemachinegenerator.smg.postprocessor.WatchDir;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			try{
				new WatchDir();
			} catch (Exception e) {}
		};
	}

}
