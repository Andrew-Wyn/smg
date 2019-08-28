package com.statemachinegenerator.smg.repository;

import com.bmeme.lib.rest.factory.mongo.GenericRepository;
import com.statemachinegenerator.smg.domain.FSMConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public interface FSMConfigurationRepository extends GenericRepository<FSMConfiguration, String> {
}
