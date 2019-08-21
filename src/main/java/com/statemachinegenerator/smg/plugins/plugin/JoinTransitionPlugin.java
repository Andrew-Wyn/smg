package com.statemachinegenerator.smg.plugins.plugin;

import com.statemachinegenerator.smg.domain.transitions.JoinTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.JoinTransitionConfigurer;

import java.util.Collections;

@TransitionPlugin
public class JoinTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = JoinTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception {
        JoinTransition joinTransition = (JoinTransition) transition;

        return transitionConfigurer
                .withJoin()
                .sources(joinTransition.getSources())
                .target(joinTransition.getTarget())
                .and();
    }
}
