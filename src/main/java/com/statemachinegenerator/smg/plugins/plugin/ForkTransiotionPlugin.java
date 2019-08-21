package com.statemachinegenerator.smg.plugins.plugin;

import com.statemachinegenerator.smg.domain.transitions.ForkTransition;
import com.statemachinegenerator.smg.domain.transitions.JoinTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.ForkTransitionConfigurer;

@TransitionPlugin
public class ForkTransiotionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = ForkTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception {
        ForkTransition forkTransition = (ForkTransition) transition;

        ForkTransitionConfigurer<String, String> forkTransitionConfigurer = transitionConfigurer.withFork();

        forkTransitionConfigurer = forkTransitionConfigurer.source(forkTransition.getSource());

        for(String targhet : forkTransition.getTargets()){
            forkTransitionConfigurer = forkTransitionConfigurer.target(targhet);
        }

        return forkTransitionConfigurer.and();
    }
}
