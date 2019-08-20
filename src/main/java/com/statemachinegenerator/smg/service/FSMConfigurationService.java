package com.statemachinegenerator.smg.service;

import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.repository.FSMConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FSMConfigurationService {

    private final FSMConfigurationRepository repository;

    public FSMConfiguration getElement(String id){
        return repository.findById(id).orElse(null);
    }

    public FSMConfiguration addElement(FSMConfiguration fmsConfiguration){
        return repository.insert(fmsConfiguration);
    }

}
