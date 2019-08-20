package com.statemachinegenerator.smg.plugins.model;

import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.domain.Transition;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

public interface TransitionTypeInterface {

    boolean check(Class typeTransition);

    StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer) throws Exception;

}
