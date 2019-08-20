package com.statemachinegenerator.smg.repository;

import com.statemachinegenerator.smg.domain.FSMConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FSMConfigurationRepository extends MongoRepository<FSMConfiguration, String> {
}
