package com.statemachinegenerator.smg.plugins.plugin;

import com.statemachinegenerator.smg.domain.transitions.ChoiceTransition;
import com.statemachinegenerator.smg.domain.transitions.HistoryTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

public class HistoryTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = ChoiceTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception{
        HistoryTransition historyTransition = (HistoryTransition) transition;

        return transitionConfigurer
                .withHistory()
                .source(historyTransition.getSource())
                .target(historyTransition.getTarget())
                .and();
    }
}
