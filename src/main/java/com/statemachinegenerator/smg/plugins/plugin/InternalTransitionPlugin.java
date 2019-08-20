package com.statemachinegenerator.smg.plugins.plugin;

import com.statemachinegenerator.smg.domain.ExternalTransition;
import com.statemachinegenerator.smg.domain.InternalTransition;
import com.statemachinegenerator.smg.domain.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@TransitionPlugin
public class InternalTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = InternalTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer) throws Exception{
        InternalTransition internalTransition = (InternalTransition) transition;

        return transitionConfigurer
                .withInternal()
                .source(internalTransition.getSource())
                .timerOnce(internalTransition.getTimer())
                .and();
    }
}
