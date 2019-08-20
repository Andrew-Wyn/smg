package com.statemachinegenerator.smg.plugins.plugin;

import com.statemachinegenerator.smg.domain.ChoiceTransition;
import com.statemachinegenerator.smg.domain.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.ChoiceTransitionConfigurer;

// improve

@TransitionPlugin
public class ChoiceTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = ChoiceTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer) throws Exception{
        ChoiceTransition choiceTransition = (ChoiceTransition) transition;

        ChoiceTransitionConfigurer<String, String> choiceTransitionConfigurer = transitionConfigurer
                .withChoice()
                .source(choiceTransition.getSource());

        choiceTransitionConfigurer = choiceTransitionConfigurer.first(choiceTransition.getFirst(), (ctx) -> true);

        for(String then : choiceTransition.getThen()){
            choiceTransitionConfigurer = choiceTransitionConfigurer.then(then, (ctx) -> false);
        }


        return  choiceTransitionConfigurer
                .last(choiceTransition.getLast())
                .and();
    }
}
