package com.statemachinegenerator.smg;

import com.bmeme.lib.libannotation.annotations.LibAction;
import com.statemachinegenerator.smg.service.FSMConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
