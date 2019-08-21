package com.statemachinegenerator.smg.plugins.model;

import com.statemachinegenerator.smg.domain.transitions.Transition;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

public interface TransitionTypeInterface {

    boolean check(Class typeTransition);

    StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception;

}
