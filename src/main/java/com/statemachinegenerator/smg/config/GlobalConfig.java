package com.statemachinegenerator.smg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
//@EnableReactiveMongoRepositories(basePackages = "com.bmeme.igt.tenq.repository")
@EnableAsync
// necessario per la creazione dei bean repository associato alle operazioni CRUD su MONGODB
@EnableMongoRepositories(basePackages = "com.statemachinegenerator.smg.repository")
public class GlobalConfig {}
