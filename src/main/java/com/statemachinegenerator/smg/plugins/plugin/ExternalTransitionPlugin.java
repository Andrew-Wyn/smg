package com.statemachinegenerator.smg.plugins.plugin;

import com.statemachinegenerator.smg.domain.ExternalTransition;
import com.statemachinegenerator.smg.domain.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@TransitionPlugin
public class ExternalTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = ExternalTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer) throws Exception{
        ExternalTransition externalTransition = (ExternalTransition) transition;

        return transitionConfigurer
                .withExternal()
                .source(externalTransition.getSource())
                .target(externalTransition.getTarget())
                .event(externalTransition.getEvent())
                //actions
                .and();
    }
}
